package com.ssmall.wap.data;

import com.csvreader.CsvReader;
import com.ssmall.wap.framework.SsMallFrame;
import com.ssmall.wap.log.Log;

import java.nio.charset.Charset;

public class SsMallHandleData {

    public SsMallFrame ssMall;

    public SsMallHandleData(SsMallFrame ssMall){
        this.ssMall = ssMall;
    }

    /**
     * 加载初始化数据
     *
     * @param filePath csv文件路径
     */
    public void initData(String filePath,String value) {

        try {

            //向map中输入指定的参数
            if(!value.isEmpty()){
                String[] skeys = value.split("!");
                for (String s : skeys){
                    String[] svalues = s.split("=");
                    if (svalues.length == 2){
                        ssMall.map.put(svalues[0],svalues[1]);
                    }
                }
            }

            Log.log("info","开始加载文件");
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(filePath,',', Charset.forName("GBK"));
            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()) {

                if (csvReader.get("key").isEmpty() || csvReader.get("key").contains("dj") || csvReader.get("key").contains("ssmall")){
                    continue;
                }

                if (csvReader.get("key").contains("seedfile")){
                    new SsMallHandleData(ssMall).initData("data/" + RunProData.city + "/" + csvReader.get("xpath") + ".csv",csvReader.get("value"));
                    continue;
                }

                // 读一整行
//                System.out.println(csvReader.getRawRecord());
                // 读这行的某一列
                ssMall.runData.add(csvReader.get("key") + "，" +
                        (csvReader.get("value").contains("!total_number") ?
                                csvReader.get("value").split("!")[0] + ssMall.map.get("total_number") :
                                csvReader.get("value")) + "，" +
                        csvReader.get("xpath") + "，" +
                        csvReader.get("depict"));
                // 读这行的某一列
                //System.out.println(csvReader.get("Link"));

                //String[] headers = {"编号","姓名","年龄"};
                //csvWriter.writeRecord(headers);
                //String[] content = {"12365","张山","34"};
                //csvWriter.writeRecord(content);
                //csvWriter.close();
            }
            csvReader.close();
        }catch (Exception e) {
            Log.log("error","数据加载失败");
            e.printStackTrace();
        }
        Log.log("info","数据加载完成");
    }

    /**
     *
     *初始化ssmall的url地址
     *
     */
    private void initUrl(String[] urls) {
        for(String url : urls){
            String[] s = url.split("@");
            if(s.length == 2){
                ssMall.map.put(s[0],s[1]);
            }
        }
    }

    /**
     *
     *初始化ssmall的url地址
     *
     */
    public void isCity(String city) {
        if (city.contains("dj")){
            initUrl(RunProData.djurls);
            return;
        }

        if (city.contains("ssmall")){
            initUrl(RunProData.ssurls);
            return;
        }

        if (city.contains("eng")){
            initUrl(RunProData.engurls);
            return;
        }

    }

    /**
     * 清空上一个用例的操作
     */
    public void clear(){
        ssMall.runData.clear();
        ssMall.map.clear();
    }

}
