/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.jvm.gc;


/**
 * @author Yue Chang
 * @ClassName: FinalizeEscapeGC
 * @Description: 一次对象自我拯救的示例
 * @date 2018/5/16 20:41
 */
public class FinalizeEscapeGC {

    private static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am still alive :) ");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {

        SAVE_HOOK = new FinalizeEscapeGC();

        // 对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停0.5秒以等待执行
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :( ");
        }

        // 下面这段代码与上面的完全相同，但是这次自救却失败了
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停0.5秒以等待执行
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :( ");
        }
    }
}
/*

此段代码演示了两点：
1、对象可以在被GC时自我拯救；
2、这种自救的机会只有一次，因为一个对象finalize方法最多只会被系统自动调用一次


程序输出：
finalize method executed!
yes, i am still alive :)
no, i am dead :(
 */
