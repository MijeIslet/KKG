package com.ssmall.wap.useCase;

import com.ssmall.wap.db.ConnectDB;
import com.ssmall.wap.framework.SsMallFrame;
import com.ssmall.wap.log.Log;

public class HandleStep {

    private SsMallFrame ssMallFrame;

    public HandleStep(SsMallFrame ssMallFrame){
        this.ssMallFrame = ssMallFrame;
    }

    /**
     *
     * 执行每一步操作
     *
     * @param singletons 每一步操作的内容
     */
    public void handleData(String[] singletons) {
        switch (singletons[0]) {
            //打开URL地址
            case "url":
                ssMallFrame.openUrl(singletons[2]);
                break;

            //点击指定元素
            case "click":
                ssMallFrame.clickKey(singletons[1],singletons[2]);
                break;
            //查看指定元素并点击
            case "seeandclick":
                ssMallFrame.seeandclickKey(singletons[2]);
                break;

            //在指定元素输入text文本
            case "set":
                ssMallFrame.setText(singletons[1],singletons[2]);
                break;
            //获取指定元素的text文本
            case "get":
                ssMallFrame.getText(singletons[1],singletons[2]);
                break;
            //获取指定元素的value文本
            case "getvalue":
                ssMallFrame.getValue(singletons[1],singletons[2]);
                break;

            //在指定元素上传文件
            case "upload":
                ssMallFrame.upload(singletons[1],singletons[2]);
                break;

            //检查指定text文本是否存在
            case "seetext":
                ssMallFrame.seeText(singletons[1],singletons[2]);
                break;
            //检查指定元素是否存在
            case "seexpath":
                ssMallFrame.seeXpath(singletons[2]);
                break;

            //暂停指定时间
            case "sleep":
                ssMallFrame.pause(Integer.parseInt(singletons[2]));
                break;

            //滚动指定距离
            case "scroll":
                ssMallFrame.scrollHight(singletons[1],Integer.parseInt(singletons[2]));
                break;

            //计算指定的值是否一致
            case "reckon":
                ssMallFrame.reckon(singletons[3],singletons[2]);
                break;
            //计算使用了预付卷的运算
            case "reckonyufukuan":
                ssMallFrame.reckonYufuKuan();
                break;

            //在map集合中，填入指定数值
            case "mapput":
                ssMallFrame.mapput(singletons[1],singletons[2]);
                break;

            //鼠标悬停在指定元素上
            case "hover":
                ssMallFrame.hover(singletons[2]);
                break;

            //进入iframe
            case "goiframe":
                ssMallFrame.goiframe(singletons[2]);
                break;
            //退出iframe
            case "endiframe":
                ssMallFrame.endiframe();
                break;

            //刷新页面
            case "refresh":
                ssMallFrame.refresh();
                break;

            //选择指定的下拉选择框的值
            case "list":
                ssMallFrame.list(Integer.parseInt(singletons[1]),singletons[2]);
                break;

            //选择指定的下拉选择框的值
            case "switch":
                ssMallFrame.switchWindowByIndex(Integer.parseInt(singletons[1]));
                break;

            //选择3
            case "isclick":
                ssMallFrame.isclick(singletons[1],singletons[2]);
                break;

            default:
                Log.log("error",singletons[1]);
                return;

        }
        Log.log("info",singletons[1]);
    }


}
