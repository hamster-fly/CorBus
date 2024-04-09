package com.hamster.core.inner.intercept.post

import com.hamster.core.inner.intercept.post.base.IWaitPoster
import com.hamster.core.inner.intercept.post.protol.IPostIntercept

/**
 * post链
 */
internal class PostChain(
    private val chainBean: IWaitPoster,
    private val index: Int,
    private val intercepts: List<IPostIntercept>,
) : IPostIntercept.IPostChain {

    private fun createChain(index: Int,bean: IWaitPoster) =
        PostChain(bean, index, intercepts)

    /**
     * 拦截器链执行的位置
     * 默认从第一个开始执行
     */
    fun call(){
        process(chainBean)
    }

    override fun process(poster: IWaitPoster) {
        if(index >= intercepts.size){
            throw IllegalArgumentException("Post消息拦截器数目错误，请注意检查拦截器数量")
        }
        intercepts[index].intercept(createChain(index + 1,poster))
    }

    override fun getPoster(): IWaitPoster {
        return chainBean
    }
}
