package com.yingu.poc.webservice;

import javax.jws.WebService;


/**
 * 
 * 
 *
 * Description: 提供给运营平台接口
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月7日上午9:37:12    tianye       V1.0        
 * </pre>
 */

@WebService
public interface ForOmWebService {

	/**
	 * 
	* @Author tianye
	* @Description: TODO C#推送月还数据 (xml格式数据)
	* @return String
	* @throws
	 */
	public String syncRepaymentData(String xmlData);
	
	/**
	 * 
	* @Author tianye
	* @Description: 发起月还 
	* @return String
	* @throws
	 */
	public String reRepayment();
}
