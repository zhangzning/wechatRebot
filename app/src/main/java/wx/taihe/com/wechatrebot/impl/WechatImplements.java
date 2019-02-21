package wx.taihe.com.wechatrebot.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import wx.taihe.com.wechatrebot.LogUtil;
import wx.taihe.com.wechatrebot.inter.WechatInterface;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;

public class WechatImplements extends WechatInterface{

    public WechatImplements(Context context, ClassLoader classLoader) {
        super(context, classLoader);
        Log.i("Wechat Rebot Log","初始化微信聊天机器人-聊天模块加载完成");
    }

    @Override
    public void autoRepeat() {
        //接收微信消息的类
        Class<?> receiveClass = XposedHelpers.findClass("com.tencent.mm.booter.notification.b",classLoader);
        XposedHelpers.findAndHookMethod(receiveClass, "b", String.class, String.class, int.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                final String userAccount = param.args[0].toString();
                final String userMessage = param.args[1].toString();
                Log.i("Wechat Rebot Log","收到微信消息!发件人Mac:"+userAccount+",消息:"+userMessage);

                if(userMessage.contains("@帅逼")) {

                    //开始Hook发微信的内容
                    Class<?> kernelGClass = XposedHelpers.findClass("com.tencent.mm.kernel.g", classLoader);
                    //au.DK() 方法中得g.DQ()方法，返回static g类
                    Object g_b = XposedHelpers.callStaticMethod(kernelGClass, "DQ");
                    //
                    Object dJT = XposedHelpers.getObjectField(g_b, "dKW");


                    Class<?> p_Class = XposedHelpers.findClass("com.tencent.mm.ah.p", classLoader);
                    //获取用于发送微信消息的类h的父类m
                    Class<?> m_Class = XposedHelpers.findClass("com.tencent.mm.ah.m", classLoader);
                    //Hook p类的a(m,int)方法
                    Method a_Method = XposedHelpers.findMethodExact(p_Class, "a", m_Class, int.class);

                    Object a_Method_v = XposedHelpers.callStaticMethod(p_Class, "a", dJT);
                    Log.i("Dump Stack: ", "---------------start----------------");
                    Throwable ex = new Throwable();
                    StackTraceElement[] stackElements = ex.getStackTrace();
                    if (stackElements != null) {
                        for (int i = 0; i < stackElements.length; i++) {
                            Log.i("Dump Stack"+i+": ", stackElements[i].getClassName()+"----"+stackElements[i].getFileName()+"----" + stackElements[i].getLineNumber()+"----" +stackElements[i].getMethodName());
                        }
                    }
                    Log.i("Dump Stack: ", "---------------over----------------");

                    //调用h类,准备给要发送的消息赋值
                    Class<?> h_Class_sendMessageValue = XposedHelpers.findClass("com.tencent.mm.modelmulti.h", classLoader);
                    //赋值
                    Object h = XposedHelpers.newInstance(h_Class_sendMessageValue, new Class[]{String.class, String.class, int.class, int.class, Object.class}, userAccount, "接收到:'" + userAccount + "',发送的消息:" + userMessage, 1, 1, new HashMap<String, String>() {{
                        put(userAccount, userAccount);
                    }});
                    //做a(m,int)的参数
                    Object[] aValue = new Object[]{h, 0};
                    //测试调用
                    XposedBridge.invokeOriginalMethod(a_Method, a_Method_v, aValue);
                    LogUtil.i(kernelGClass, g_b, a_Method_v);


/*
                    Log.i("Dump Stack: ", "---------------start----------------");
                    Throwable ex = new Throwable();
                    StackTraceElement[] stackElements = ex.getStackTrace();
                    if (stackElements != null) {
                        for (int i = 0; i < stackElements.length; i++) {
                            Log.i("Dump Stack"+i+": ", stackElements[i].getClassName()+"----"+stackElements[i].getFileName()+"----" + stackElements[i].getLineNumber()+"----" +stackElements[i].getMethodName());
                        }
                    }
                    Log.i("Dump Stack: ", "---------------over----------------");*/
                }

            }
        });
    }

    @Override
    public void addFriends() {
        //加好友操作,获取输入参数测试
        Class<?> FTSAfu = XposedHelpers.findClass("com.tencent.mm.plugin.fts.ui.FTSMainUI$6",classLoader);
        //Class<?> FTSmu = XposedHelpers.findClass("",classLoader);
        XposedHelpers.findAndHookMethod(FTSAfu,"onSceneEnd",int.class,int.class,String.class,XposedHelpers.findClass("com.tencent.mm.ah.m",classLoader),new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                //此处注释，先获取到bnm实例化的bhH，需要获取tcA的值是多少，get方法获取mVar的bhH方法的返回值
                //源代码bnm bhH = ((com.tencent.mm.plugin.messenger.a.f) mVar).bhH();
                //            if (bhH.tcA > 0) {
                Log.i("Wechat bhH.tcA 的值为",""+XposedHelpers.findField(XposedHelpers.findClass("com.tencent.mm.protocal.c.bnm",classLoader),"tcA").get(XposedHelpers.callMethod((param.args[3]),"bhH")));

                Log.i("Wechat bhH.tGq 的值为",""+XposedHelpers.findField(XposedHelpers.findClass("com.tencent.mm.protocal.c.bnm",classLoader),"tGq").get(XposedHelpers.callMethod((param.args[3]),"bhH")));


                //BG为搜索的值
                Log.i("Wechat BG 的值为",""+XposedHelpers.findField(XposedHelpers.findClass("com.tencent.mm.plugin.fts.ui.FTSMainUI$6",classLoader),"BG").get(param.thisObject).toString());


                Log.i("Wechat Rebot Log before","输入搜索的文本为:"+ param.args[0]+"   "+param.args[1]+"   "+param.args[2]);


            }

        });
    }

    @Override
    public void addFriendsToUI() {
        //针对搜索结果的返回值
        Class<?> bbsr = XposedHelpers.findClass(" com.tencent.mm.br.d",classLoader);
        XposedHelpers.findAndHookMethod(bbsr,"b",Context.class,String.class,String.class,Intent.class,Bundle.class,new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Intent intent = (Intent)param.args[3];
                Log.i("mm.br.d$b方法的参数",""+param.args[1].toString()+"   "+param.args[2].toString());
                Bundle bundle = intent.getExtras();
                for(String key : bundle.keySet()){
                    Log.i("intent 所有key以及值",key+"   "+bundle.get(key));
                }
            }
        });
    }

}
