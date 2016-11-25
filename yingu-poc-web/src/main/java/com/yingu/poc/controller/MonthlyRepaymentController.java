package com.yingu.poc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yingu.poc.common.base.PageModel;
import com.yingu.poc.common.utils.DateUtil;
import com.yingu.poc.common.utils.ExcelUtil;
import com.yingu.poc.enums.DeductStatus;
import com.yingu.poc.enums.PlantformType;
import com.yingu.poc.pojo.MonthlyRepayment;
import com.yingu.poc.pojo.PayChannelBean;
import com.yingu.poc.service.MonthlyRepaymentService;
import com.yingu.poc.webservice.CallOmWebService;

@Controller
@RequestMapping(value="/monthlyRepaymentController")
public class MonthlyRepaymentController {

	@Autowired
	public MonthlyRepaymentService monthlyRepaymentService;
	
	@Value("${om.reportDataForPage.url}")
	private String omReportDataForPage;
	
	@Value("${om.allReportData.url}")
	private String omAllReportDataUrl;
	
	@Autowired
	private CallOmWebService callOmWebServiceImpl;
	
	/**
	 * 
	* @Author tianye
	* @Description: 跳转到查询页面 
	* @return String
	* @throws
	 */
	@RequestMapping(value="/showListPage")
	public String showListPage(){
		
		return "app/report/report";
	}
	
	/**
	 * 
	* @Author tianye
	* @Description: 查询分页数据 
	* @return JSONPObject
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/showList")
	public Map<String,Object>  showList(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		MonthlyRepayment repayment = new MonthlyRepayment();
		PageModel page = new PageModel();
		page.setPageNo(Integer.valueOf(request.getParameter("page")));
		page.setPageSize(Integer.valueOf(request.getParameter("rows")));

		String deductStatus = request.getParameter("deductStatus");
		String repayDate = request.getParameter("repayDate");
		String orderNum = request.getParameter("orderNum");
		String plantformType = request.getParameter("plantformType");
		if(deductStatus!=null && !deductStatus.isEmpty()){
			repayment.setDeductStatus(Integer.valueOf(deductStatus));
		}
		if(repayDate!=null && !repayDate.isEmpty()){
			repayment.setRepayDate(Date.valueOf(repayDate));
		}
		if(orderNum!=null && !orderNum.isEmpty()){
			repayment.setOrderNum(orderNum);
		}
		if(plantformType!=null && !plantformType.isEmpty()){
			repayment.setPayChannel(plantformType);
		}
		page = monthlyRepaymentService.findListByPage(repayment, page);
		
		BigDecimal totalMoney = monthlyRepaymentService.totalMoneySum(repayment);
		
//		map.put("totalMoney", totalMoney);
//		msg.setRows(page.getRows());
//		msg.setTotal(page.getTotal());
		map.put("totalMoney", totalMoney);
		map.put("rows", page.getRows());
		map.put("total", page.getTotal());
		return map;
	}
	
//	@ResponseBody
//	@RequestMapping(value="/showList")
//	public Map<String,Object> showList(HttpServletRequest request){
//		ReportRequest reportRequest = new ReportRequest();
//		reportRequest.setPageIndex(Integer.valueOf(request.getParameter("page")));
//		reportRequest.setPageSize(Integer.valueOf(request.getParameter("rows")));
//
//		String deductStatus = request.getParameter("deductStatus");
//		String repayDate = request.getParameter("repayDate");
//		String orderNum = request.getParameter("orderNum");
//		String plantformType = request.getParameter("plantformType");
//		String failReason = request.getParameter("failReason");
//		String requestId = request.getParameter("requestId");
//		
//		if(deductStatus!=null && !deductStatus.isEmpty()){
//			reportRequest.setRepaymentStatus(Integer.valueOf(deductStatus));
//		}
//		if(repayDate!=null && !repayDate.isEmpty()){
//			reportRequest.setRepayDate(repayDate);
//		}
//		if(orderNum!=null && !orderNum.isEmpty()){
//			reportRequest.setOrderNum(orderNum);
//		}
//		if(plantformType!=null && !plantformType.isEmpty()){
//			reportRequest.setPayChannel(Integer.valueOf(plantformType));
//		}
//		if(failReason!=null && !failReason.isEmpty()){
//			reportRequest.setFailedReason(failReason);
//		}
//		if(requestId!=null && !requestId.isEmpty()){
//			reportRequest.setRequestId(requestId);
//		}
//		
//		Map<String,Object> map = new HashMap<String, Object>();
//		
//		try{
//			String result = HttpUtil.sendPost(omReportDataForPage, JaxbUtil.convertToXml(reportRequest));
//			ReportResponse reportResponse = new ReportResponse();
//			if(result!=null && !"".equals(result)){
//				reportResponse = JaxbUtil.converyToJavaBean(result, ReportResponse.class);
//			}
//			map.put("totalMoney", reportResponse.getTotalMoney());
//			map.put("rows", reportResponse.getList());
//			map.put("total", reportResponse.getTotal());
//		}catch(Exception e){
//			map.put("totalMoney", 0);
//			map.put("rows", 0);
//			map.put("total", 0);
//		}
//		return map;
//	}
	
	/**
	 * 
	* @Author tianye
	* @Description: 导出数据 
	* @return String
	* @throws
	 */
	@RequestMapping(value="/export")
	public String exportMonthlyRepaymentData(HttpServletRequest request, HttpServletResponse response){
		MonthlyRepayment repayment = new MonthlyRepayment();
		String deductStatus = request.getParameter("deductStatus");
		String repayDate = request.getParameter("repayDate");
		String orderNum = request.getParameter("orderNum");
		String plantformType = request.getParameter("plantformType");
		if(deductStatus!=null && !deductStatus.isEmpty()){
			repayment.setDeductStatus(Integer.valueOf(deductStatus));
		}
		if(repayDate!=null && !repayDate.isEmpty()){
			repayment.setRepayDate(Date.valueOf(repayDate));
		}
		if(orderNum!=null && !orderNum.isEmpty()){
			repayment.setOrderNum(orderNum);
		}
		if(plantformType!=null && !plantformType.isEmpty()){
			repayment.setPayChannel(plantformType);
		}
		
		String title = "月还报表";
		String[] header = {"进件编号","流水号","支付渠道","月还扣款日期","月还金额","贷款期限","借款人","支行名称","卡号","交易状态","交易状态信息","交易时间","门店名称","门店所在城市","门店区域"};
		String[] columns = {"orderNum","requestId","plantformType","repayDate","payAmounts","loanDeadline","userName","bankDetailed","bankCard","deductStatus","transactionStatusMsg","deductTime","storeName","storeCity","storeArea"};
		List<MonthlyRepayment> list = monthlyRepaymentService.findListForExport(repayment);
		List<Map<String,Object>> data = convertDataForExport(list);
		
		BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
		try {
			XSSFWorkbook workbook = ExcelUtil.exportExcel(title, header, columns, data);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbook.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
							+ new String((title + ".xlsx").getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
		
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
	        // Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			  bos.write(buff, 0, bytesRead);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(bis!=null){
					bis.close();
				}
				if(bos!=null){
					bos.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
//	@RequestMapping(value="/export")
//	public String exportMonthlyRepaymentData(HttpServletRequest request, HttpServletResponse response){
//		ReportRequest reportRequest = new ReportRequest();
//		String deductStatus = request.getParameter("deductStatus");
//		String repayDate = request.getParameter("repayDate");
//		String orderNum = request.getParameter("orderNum");
//		String plantformType = request.getParameter("plantformType");
//		String failReason = request.getParameter("failReason");
//		if(deductStatus!=null && !deductStatus.isEmpty()){
//			reportRequest.setRepaymentStatus(Integer.valueOf(deductStatus));
//		}
//		if(repayDate!=null && !repayDate.isEmpty()){
//			reportRequest.setRepayDate(repayDate);
//		}
//		if(orderNum!=null && !orderNum.isEmpty()){
//			reportRequest.setOrderNum(orderNum);
//		}
//		if(plantformType!=null && !plantformType.isEmpty()){
//			reportRequest.setPayChannel(Integer.valueOf(plantformType));
//		}
//		if(failReason!=null && !failReason.isEmpty()){
//			reportRequest.setFailedReason(failReason);
//		}
//		String title = "月还报表";
//		String[] header = {"进件编号","支付渠道","月还扣款日期","月还金额","贷款期限","借款人","支行名称","卡号","交易状态","交易状态信息","交易时间","门店名称","门店所在城市","门店区域"};
//		String[] columns = {"orderNum","plantformType","repayDate","payAmounts","loanDeadline","userName","bankDetailed","bankCard","deductStatus","transactionStatusMsg","deductTime","storeName","storeCity","storeArea"};
//		
//		String result = HttpUtil.sendPost(omAllReportDataUrl, JaxbUtil.convertToXml(reportRequest));
//		List<MonthlyRepayment> list = JaxbUtil.converyToJavaBean("ArrayOfRepayData", result, MonthlyRepayment.class);
//		List<Map<String,Object>> data = convertDataForExport(list);
//		
//		BufferedInputStream bis = null;
//	    BufferedOutputStream bos = null;
//		try {
//			XSSFWorkbook workbook = ExcelUtil.exportExcel(title, header, columns, data);
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			workbook.write(os);
//			byte[] content = os.toByteArray();
//			InputStream is = new ByteArrayInputStream(content);
//			// 设置response参数，可以打开下载页面
//			response.reset();
//			response.setContentType("application/vnd.ms-excel;charset=utf-8");
//			response.setHeader("Content-Disposition", "attachment;filename="
//							+ new String((title + ".xlsx").getBytes(), "iso-8859-1"));
//			ServletOutputStream out = response.getOutputStream();
//		
//			bis = new BufferedInputStream(is);
//			bos = new BufferedOutputStream(out);
//			byte[] buff = new byte[2048];
//			int bytesRead;
//	        // Simple read/write loop.
//			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//			  bos.write(buff, 0, bytesRead);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try{
//				if(bis!=null){
//					bis.close();
//				}
//				if(bos!=null){
//					bos.close();
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}

	/**
	 * 
	* @Author tianye
	* @Description: 同步数据 
	* @return Map
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/synchronizedDate")
	public Map<String,Object> synchronizedData(HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "success");
		
		String syncDate = request.getParameter("syncDate");
		
		try{
				callOmWebServiceImpl.synMonthlyRepaymentData(syncDate);
			
		}catch(Exception e){
			result.put("result", "error");
			result.put("msg", "同步数据失败");
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 *
	 * @param list
	 * @return
	 */
	private List<Map<String,Object>> convertDataForExport(List<MonthlyRepayment> list){
		List<Map<String,Object>> listMap = new ArrayList<>();
		for(MonthlyRepayment mr : list){
			Map<String,Object> map = new HashMap<>();
			map.put("orderNum", mr.getOrderNum());
			try{
				PlantformType plantformType = PlantformType.createByValue(mr.getPayChannel());
				if (plantformType != null) {
					map.put("plantformType", plantformType.getDesc());
				}
			}catch(Exception e){
				map.put("plantformType", mr.getPayChannel());
			}
			map.put("repayDate", DateUtil.formatting(mr.getRepayDate(), DateUtil.FORMATTING_DATE));
			map.put("payAmounts", mr.getPayAmounts());
			map.put("loanDeadline", mr.getLoanDeadline());
			map.put("userName", mr.getUserName());
			map.put("bankDetailed", mr.getBankDetailed());
			map.put("bankCard", mr.getBankCard());
			map.put("requestId", mr.getRequestId());
			DeductStatus deductStatus = DeductStatus.getEnum(mr.getDeductStatus());
			if (deductStatus != null) {
				map.put("deductStatus", deductStatus.getDesc()); // 处理状态
			}

//			switch (mr.getDeductStatus()) {
//			case 1:
//				map.put("deductStatus", "未处理");
//				break;
//			case 3:
//				map.put("deductStatus", "代扣成功");
//				break;
//			case 4:
//				map.put("deductStatus", "代扣失败");
//				break;
//			default:
//				map.put("deductStatus", mr.getDeductStatus());
//				break;
//			}
			map.put("transactionStatusMsg", mr.getTransactionStatusMsg());
			map.put("deductTime",mr.getDeductTime()!=null? DateUtil.formatting(mr.getDeductTime()):"");
			map.put("storeName", mr.getStoreName());
			map.put("storeCity", mr.getStoreCity());
			map.put("storeArea", mr.getStoreArea());
			map.put("requestId", mr.getRequestId());
			
			listMap.add(map);
		}
		
		return listMap;
	}
	
//	@XmlRootElement(name = "ResultInfo")
//	private static class ReportResponse{
//		
//		private Integer total;
//		
//		private BigDecimal totalMoney;
//		
//		private List<MonthlyRepayment> list;
//		
//		public Integer getTotal() {
//			return total;
//		}
//		@XmlElement(name="TotalCount")
//		public void setTotal(Integer total) {
//			this.total = total;
//		}
//		
//		public BigDecimal getTotalMoney() {
//			return totalMoney;
//		}
//		@XmlElement(name="TotalAmount")
//		public void setTotalMoney(BigDecimal totalMoney) {
//			this.totalMoney = totalMoney;
//		}
//		public List<MonthlyRepayment> getList() {
//			return list;
//		}
//		@XmlElementWrapper(name = "RepayDataList")
//		@XmlElement(name = "RepayData")
//		public void setList(List<MonthlyRepayment> list) {
//			this.list = list;
//		}
//	}
//	
//	@XmlRootElement(name = "ParamModel")
//	private static class ReportRequest{
//		private String repayDate;
//		private String orderNum;
//		private Integer plantformType;
//		private Integer repaymentStatus;
//		private String failedReason;
//		private Integer pageSize;
//		private Integer pageIndex;
//		private String requestId;
//		
//		@XmlElement(required = true,name="PageSize")
//		public Integer getPageSize() {
//			return pageSize;
//		}
//		public void setPageSize(Integer pageSize) {
//			this.pageSize = pageSize;
//		}
//		@XmlElement(required = true,name="PageIndex")
//		public Integer getPageIndex() {
//			return pageIndex;
//		}
//		public void setPageIndex(Integer pageIndex) {
//			this.pageIndex = pageIndex;
//		}
//		@XmlElement(required = true,name="RepayDate")
//		public String getRepayDate() {
//			return repayDate;
//		}
//		public void setRepayDate(String repayDate) {
//			this.repayDate = repayDate;
//		}
//		@XmlElement(required = true,name="OrderNum")
//		public String getOrderNum() {
//			return orderNum;
//		}
//		public void setOrderNum(String orderNum) {
//			this.orderNum = orderNum;
//		}
//		@XmlElement(required = true,name="PlantformType")
//		public Integer getPayChannel() {
//			return plantformType;
//		}
//		public void setPayChannel(Integer plantformType) {
//			this.plantformType = plantformType;
//		}
//		@XmlElement(required = true,name="RepaymentStatus")
//		public Integer getRepaymentStatus() {
//			return repaymentStatus;
//		}
//		public void setRepaymentStatus(Integer repaymentStatus) {
//			this.repaymentStatus = repaymentStatus;
//		}
//		@XmlElement(required = true,name="FailedReason")
//		public String getFailedReason() {
//			return failedReason;
//		}
//		public void setFailedReason(String failedReason) {
//			this.failedReason = failedReason;
//		}
//		@XmlElement(required = true,name="RequestId")
//		public String getRequestId() {
//			return requestId;
//		}
//		public void setRequestId(String requestId) {
//			this.requestId = requestId;
//		}
//		
//	}

	@ResponseBody
	@RequestMapping(value = "getPayChannelList")
	public List<PayChannelBean> getPayChannelList(){
		List<PayChannelBean> list = new ArrayList<>();
		if (PlantformType.values() != null && PlantformType.values().length > 0) {
			for (PlantformType plantformType : PlantformType.values()) {
				PayChannelBean bean = new PayChannelBean();
				bean.setId(plantformType.toString());
				bean.setName(plantformType.getDesc());
				list.add(bean);
			}
		}
		return list;
	}
}