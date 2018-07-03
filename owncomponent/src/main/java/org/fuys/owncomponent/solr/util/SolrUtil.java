package org.fuys.owncomponent.solr.util;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;

public class SolrUtil {

	private static HttpClient createClient;
	static {
		ModifiableSolrParams invariantParams = new ModifiableSolrParams();
		invariantParams.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false); // 遵循重定向
		invariantParams.set(HttpClientUtil.PROP_BASIC_AUTH_USER, "test"); // 基本认证用户名
		invariantParams.set(HttpClientUtil.PROP_BASIC_AUTH_PASS, "123");// 基本认证密码
		invariantParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 1000);// 最大允许总连接
		invariantParams.set(HttpClientUtil.PROP_ALLOW_COMPRESSION, true);// 如果服务器支持的话，允许压缩
		invariantParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 1000);// 每个主机允许的最大连接
		createClient = HttpClientUtil.createClient(invariantParams);
	}

}
