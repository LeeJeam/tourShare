package com.xmyy.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.common.EnumConstants;
import com.xmyy.pay.allinpay.ServerFactory;
import com.xmyy.pay.allinpay.YunResponse;
import com.xmyy.pay.allinpay.constants.Server;
import com.xmyy.pay.allinpay.request.GetCheckAccountFileParams;
import com.xmyy.pay.allinpay.request.ServerHandler;
import com.xmyy.pay.allinpay.response.GetCheckAccountFileResult;
import com.xmyy.pay.core.YunClient;
import com.xmyy.pay.mapper.DgCheckAccountDetailMapper;
import com.xmyy.pay.model.DgCheckAccountDetail;
import com.xmyy.pay.model.DgCheckAccountSummary;
import com.xmyy.pay.service.DgCheckAccountDetailService;
import com.xmyy.pay.service.DgCheckAccountSummaryService;
import com.xmyy.pay.service.ManageCheckAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台对账管理  服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = ManageCheckAccountService.class)
//@CacheConfig(cacheNames = "ManageCheckAccount")
public class ManageCheckAccountServiceImpl extends BaseServiceImpl<DgCheckAccountDetail, DgCheckAccountDetailMapper> implements ManageCheckAccountService {
    private static Logger logger = LoggerFactory.getLogger(ManageCheckAccountServiceImpl.class);
    private static YunClient yunClient = YunClient.getInstance();

    @Resource
    private DgCheckAccountDetailService checkAccountDetailService;
    @Resource
    private DgCheckAccountSummaryService checkAccountSummaryService;

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object getCheckAccountFile(String date, Long fileType) {
        ServerHandler handler = ServerFactory.merchantHandler(Server.Merchant.GETCHECKACCOUNTFILE);
        GetCheckAccountFileParams params = new GetCheckAccountFileParams();
        params.setDate(date);
        params.setFileType(fileType);

        YunResponse response = yunClient.doRequest(handler, params, GetCheckAccountFileResult.class);
        if (response.isOK()) {
            GetCheckAccountFileResult result = (GetCheckAccountFileResult) response.getResult();
            String url = result.getUrl();
            try {
                downLoadAndanalysis(url, fileType);
                logger.info("对账文件解析成功 =======>{}", date);

//                downLoadAndSave(url, date + fileType + ".txt", "d:/An");
//                logger.info("对账文件下载成功 =======> " + date);
            } catch (IOException e) {
                logger.error("对账文件解析错误：{}", e);
                //TODO 解析不成功的后续动作，待补完
            }
        }
        return response.isOK();
    }


    /**
     * 下载对账文件并解析
     */
    private void downLoadAndanalysis(String urlStr, Long fileType) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        //解析详细对账文件
        if (fileType == EnumConstants.FileType.DETAIL.getValue().longValue()) {
            String line;
            int count = 0;
            List<DgCheckAccountDetail> detailList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                //根据换行符切割
                for (String s : line.split("\\r?\\n")) {
                    if (count == 0) { //第一行文字不解析，跳过
                        continue;
                    }

                    String[] strs = line.split("\\|");

                    DgCheckAccountDetail detail = new DgCheckAccountDetail();
                    detail.setOrderNo(strs[0]);
                    detail.setOrderType(Integer.parseInt(strs[1]));
                    detail.setAmount(Long.parseLong(strs[2]));
                    detail.setFee(Long.parseLong(strs[3]));
                    detail.setTime(strs[4]);
                    detail.setBizOrderNo(strs[5]);
                    detailList.add(detail);
                }
                count++;
            }
            checkAccountDetailService.updateBatch(detailList);
            logger.info("详细对账文件解析完毕，共{}行", count);
        }

        //解析汇总对账文件
        if (fileType == EnumConstants.FileType.SUMMARY.getValue().longValue()) {
            String line;
            int count = 0;
            List<DgCheckAccountSummary> summaryList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                for (String s : line.split("\\r?\\n")) {
                    if (count == 0) {
                        continue;
                    }

                    String[] strs = line.split("\\|");

                    DgCheckAccountSummary summary = new DgCheckAccountSummary();
                    summary.setOrderTotal(Integer.parseInt(strs[0]));
                    summary.setOrderAmount(Long.parseLong(strs[1]));
                    summary.setDepositTotal(Integer.parseInt(strs[2]));
                    summary.setDepositAmount(Long.parseLong(strs[3]));
                    summary.setConsumeTotal(Integer.parseInt(strs[4]));
                    summary.setConsumeAmount(Long.parseLong(strs[5]));
                    summary.setProtocolConsumeTotal(Integer.parseInt(strs[6]));
                    summary.setProtocolConsumeAmount(Long.parseLong(strs[7]));
                    summary.setWithdrawTotal(Integer.parseInt(strs[8]));
                    summary.setWithdrawAmount(Long.parseLong(strs[9]));
                    summary.setTransTotal(Integer.parseInt(strs[10]));
                    summary.setTransAmount(Long.parseLong(strs[11]));
                    summary.setAgentCollectTotal(Integer.parseInt(strs[12]));
                    summary.setAgentCollectAmount(Long.parseLong(strs[13]));
                    summary.setProtocolAgentCollectTotal(Integer.parseInt(strs[14]));
                    summary.setProtocolAgentCollectAmount(Long.parseLong(strs[15]));
                    summary.setSignalAgentPayTotal(Integer.parseInt(strs[16]));
                    summary.setSignalAgentPayAmount(Long.parseLong(strs[17]));
                    summary.setBatchAgentPayTotal(Integer.parseInt(strs[18]));
                    summary.setBatchAgentPayAmount(Long.parseLong(strs[19]));
                    summary.setCrossAppTransTotal(Integer.parseInt(strs[20]));
                    summary.setCrossAppTransAmount(Long.parseLong(strs[21]));
                    summary.setTransCreditorRightTotal(Integer.parseInt(strs[22]));
                    summary.setTransCreditorRightAmount(Long.parseLong(strs[23]));
                    summary.setRefundTotal(Integer.parseInt(strs[24]));
                    summary.setRefundAmount(Long.parseLong(strs[25]));
                    summary.setNoBiddersRefundTotal(Integer.parseInt(strs[26]));
                    summary.setNoBiddersRefundAmount(Long.parseLong(strs[27]));
                    summary.setStartOrderNo(strs[28]);
                    summary.setEndOrderNo(strs[29]);
                    summaryList.add(summary);
                }
                count++;
            }
            checkAccountSummaryService.updateBatch(summaryList);
            logger.info("汇总对账文件解析完毕，共{}行", count);
        }
    }


    /**
     * 下载对账文件并保存到本地（测试用）
     */
    private void downLoadAndSave(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();

        //获取字节数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }

        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        fos.close();
        inputStream.close();
    }


    /**
     * 从输入流中获取字节数组
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
