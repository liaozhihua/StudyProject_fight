package com.xm.studyproject.java;

import android.os.AsyncTask;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class UseAsynckTask {

    public void test() {
        IAsyncTask iAsyncTask = new IAsyncTask();
        iAsyncTask.execute("url1", "url2", "url3");
        //针对上一种利用 单线程存在的问题 提供了一种一定数量的线程池
        //iAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"url1", "url2", "url3");
    }

    //泛型参数1：其实这个地方是一个集合
    //参数2：是进度条的进度 也是一个集合
    //参数3：是结果值
    private class IAsyncTask extends AsyncTask<String, Integer, String> {
        //args1 就是上面的参数1
        //这个方法就是在异步线程里面执行的 就是真实任务的执行
        protected String doInBackground(String... args1) {
            int length = args1.length;
            Log.i(TAG, "doInBackground in:" + args1[0]);
            Log.i(TAG, "doInBackground in:length:" + length);
            int times = 0;
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                    times++;
                    //注意 非得调用这个方法
                    //不然onProgressUpdate方法不会被调用到
                    publishProgress(i*10);//提交之后，会执行onProcessUpdate方法
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, "doInBackground out");
            return "over:" + times;
        }

        /**
         * 在调用cancel方法后会执行到这里
         */
        //也是运行在主线程上
        protected void onCancelled() {
            Log.i(TAG, "onCancelled");
        }

        /**
         * 在doInbackground之后执行
         */
        //这个方法是在主线程执行的
        //args3值是doInBackground方法的返回值
        protected void onPostExecute(String args3) {
            Log.i(TAG, "onPostExecute:" + args3);
        }

        /**
         * 在doInBackground之前执行
         */
        //这个方法也是在主线程执行的
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        /**
         * 特别赞一下这个多次参数的方法，特别方便
         *
         * @param args2
         */
        //进度条更新
        //args2[0]是publishProgress方法传入的值
        @Override
        protected void onProgressUpdate(Integer... args2) {
            int length = args2.length;
            Log.i(TAG, "args2.length:" + length);
            Log.i(TAG, "onProgressUpdate:" + args2[0]);
        }
    }
}
