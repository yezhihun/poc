package com.yingu.poc.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yingu.poc.common.base.PageModel;
import com.yingu.poc.common.utils.DateUtil;
import com.yingu.poc.job.MonthlyRepaymentJob;
import com.yingu.poc.service.QrtzTriggerService;


/**
 * Description: 月还定时任务
 *
 * @author tianye
 * @version V1.0
 *          <pre>
 *          Modification History:
 *          Date         Author      Version     Description
 *          ------------------------------------------------------------------
 *          2016年2月17日下午2:34:15    tianye       V1.0
 *          </pre>
 */
@Controller
@RequestMapping(value = "/qrtzTriggerController")
public class QrtzTriggerController {

    @Autowired
    private QrtzTriggerService qrtzTriggerService;

    @Value("${normal.monthlyRepayment.time}")
    private String normalRepaymentTime;

    @Value("${next.monthlyRepayment.time}")
    private String nextRepaymentTime;

    @Value("${montlyRepayment.days}")
    private String repaymentDays;

    /**
     * @return String
     * @throws
     * @Author tianye
     * @Description: 跳转至任务列表页面
     */
    @RequestMapping(value = "/showPage")
    public String showPage() {

        return "app/monthly/job";
    }

    /**
     * @return List<Map<String,Object>>
     * @throws
     * @Author tianye
     * @Description: 查询显示月还任务列表
     */
    @ResponseBody
    @RequestMapping(value = "/showListJob")
    public PageModel showListJobByPage(HttpServletRequest request) {

        PageModel pageModel = new PageModel();
        pageModel.setPageNo(Integer.valueOf(request.getParameter("page")));
        pageModel.setPageSize(Integer.valueOf(request.getParameter("rows")));

        Map<String, Object> param = new HashMap<String, Object>();

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if (startDate != null && !startDate.equals("")) {
            param.put("startDate", startDate);
        }
        if (endDate != null && !endDate.equals("")) {
            param.put("endDate", endDate);
        }

        pageModel = qrtzTriggerService.findListByPage(param, pageModel);
        return pageModel;
    }

    /**
     * @return Map<String,Object>
     * @throws
     * @Author tianye
     * @Description: 根据月还日批量生成月还任务
     */
    @ResponseBody
    @RequestMapping(value = "/addBatchJob")
    public Map<String, Object> addNormalJob(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", "success");
        try {
            String desc = URLDecoder.decode(request.getParameter("desc"), "utf-8");
            String dateStr = request.getParameter("date");
            List<String> times = createCronByDateBatch(dateStr);

            for (String time : times) {
                String cron = DateUtil.convertCronExpression(time);
                qrtzTriggerService.createSimpleJob(desc, cron, MonthlyRepaymentJob.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "error");
            map.put("msg", "操作失败");
        }
        return map;
    }

    /**
     * @return List<String>
     * @throws
     * @Author tianye
     * @Description: 用于批量创建月还 时间表达式
     */
    private List<String> createCronByDateBatch(String dateStr) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        Date date = sdf.parse(dateStr);
        List<String> list = new ArrayList<String>();
        int count = Integer.valueOf(repaymentDays);
        for (int i = 0; i <= count; i++) {
            if (i == 0) {
                String[] timeArray = normalRepaymentTime.split(",");
                for (String str : timeArray) {
                    list.add(dateStr + " " + str);
                }
            } else {
                Date newDate = DateUtils.addDays(date, i);
                String newDatestr = sdf.format(newDate);
                String[] timeArray = nextRepaymentTime.split(",");
                for (String str : timeArray) {
                    list.add(newDatestr + " " + str);
                }
            }
        }
        return list;
    }

    /**
     * @return Map<String,Object>
     * @throws
     * @Author tianye
     * @Description: 添加定时任务
     */
    @ResponseBody
    @RequestMapping(value = "/addJob")
    public Map<String, Object> addJob(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", "success");
        try {
            //任务描述
            String desc = request.getParameter("desc");
            //执行时间
            String dateStr = request.getParameter("date");
            String cron = DateUtil.convertCronExpression(dateStr);
            checkedDate(dateStr); // 校验时间是否小于当前时间
            qrtzTriggerService.createSimpleJob(desc, cron, MonthlyRepaymentJob.class);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "error");
            map.put("msg", e.getMessage());
        }

        return map;
    }

//    /**
//     * @return String
//     * @throws
//     * @Author tianye
//     * @Description: 根据执行时间生成 表达式
//     */
//    private String createCronByDate(String dateStr) {
//        dateStr = dateStr.replace("-", " ").replace(":", " ").trim();
//        String[] s = dateStr.split(" ");
//        StringBuffer sb = new StringBuffer();
//        for (int i = s.length - 1; i >= 0; i--) {
//            sb.append(s[i] + " ");
//            if (i == 1) {
//                sb.append("? ");
//            }
//        }
//        return sb.toString().trim();
//    }

    /**
     * @return Map<String,Object>
     * @throws
     * @Author tianye
     * @Description: 删除任务
     */
    @ResponseBody
    @RequestMapping(value = "/deleteJob")
    public Map<String, Object> deleteJob(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", "success");
        String jobName = request.getParameter("jobName");
        try {
            qrtzTriggerService.discardJob(jobName);
            qrtzTriggerService.deleteBackupJob(jobName);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "error");
            map.put("msg", "添加失败");
        }

        return map;
    }

    private void checkedDate(String date) throws Exception {
        Date paramDate = DateUtil.formatting(date, DateUtil.FORMATTING_DATETIME);
        int compare = DateUtil.compareTo(paramDate, DateUtil.getDate());
        if(compare != 1){
            throw new Exception("选择的时间小于当前时间");
        }
    }

    /**
     * @return Map<String,Object>
     * @throws
     * @Author tianye
     * @Description: 修改任务执行时间
     */
    @SuppressWarnings("deprecation")
    @ResponseBody
    @RequestMapping(value = "/updateCron")
    public Map<String, Object> updateCronByName(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            String date = request.getParameter("date");
            checkedDate(date); // 校验时间是否小于当前时间
            date = URLDecoder.decode(date);
            String triggerName = request.getParameter("triggerName");
            result.put("result", "success");
            String cron = DateUtil.convertCronExpression(date);
            if (CronExpression.isValidExpression(cron)) {
                qrtzTriggerService.resetCronByTirggerName(cron, triggerName);
            } else {
                result.put("result", "error");
                result.put("msg", "表达式错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "error");
            result.put("msg", e.getMessage());
        }

        return result;
    }


    /**
     * @return Map<String,Object>
     * @throws
     * @Author tianye
     * @Description: 重新启动任务
     */
    @ResponseBody
    @RequestMapping(value = "/startJob/{triggerName}/{jobName}")
    public Map<String, Object> startJobStatus(@PathVariable String triggerName, @PathVariable String jobName) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "success");
        try {
            qrtzTriggerService.startJob(triggerName, jobName);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "error");
            result.put("msg", e.getMessage());
        }

        return result;
    }

    /**
     * @return Map<String,Object>
     * @throws
     * @Author tianye
     * @Description: 暂停任务
     */
    @ResponseBody
    @RequestMapping(value = "/pauseJob/{triggerName}/{jobName}")
    public Map<String, Object> pauseJobStatus(@PathVariable String triggerName, @PathVariable String jobName) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "success");
        try {
            qrtzTriggerService.pauseJob(triggerName, jobName);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "error");
            result.put("msg", "暂停失败!");
        }

        return result;
    }
}
