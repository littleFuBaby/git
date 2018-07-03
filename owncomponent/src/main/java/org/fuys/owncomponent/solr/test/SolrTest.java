package org.fuys.owncomponent.solr.test;

import java.io.IOException;
import java.util.Random;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

public class SolrTest {
	
	private static HttpClient createClient;
	static {
		ModifiableSolrParams invariantParams = new ModifiableSolrParams();
//		invariantParams.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false); // 遵循重定向
		invariantParams.set(HttpClientUtil.PROP_BASIC_AUTH_USER, "test"); // 基本认证用户名
		invariantParams.set(HttpClientUtil.PROP_BASIC_AUTH_PASS, "123");// 基本认证密码
//		invariantParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 1000);// 最大允许总连接
		invariantParams.set(HttpClientUtil.PROP_ALLOW_COMPRESSION, true);// 如果服务器支持的话，允许压缩
		invariantParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 1000);// 每个主机允许的最大连接
		createClient = HttpClientUtil.createClient(invariantParams);
	}

	private static final String SOLR_URL = "http://47.100.18.54:8708/solr/sxyp_core";

	public static HttpSolrClient getClient() {
		return new HttpSolrClient.Builder(SOLR_URL).withHttpClient(createClient).withConnectionTimeout(1000).withSocketTimeout(10000).build();
	}

	public static void main(String[] args) throws SolrServerException, IOException {

		HttpSolrClient client = getClient();
		
		SolrInputDocument doc = new SolrInputDocument();
		Integer num = new Random().nextInt(100);
		num = 12345;
		doc.addField("id", num.intValue());
		doc.addField("name", "test-" + num.intValue());
		System.out.println(num);

		client.add(doc);
		client.commit();

		SolrQuery query = new SolrQuery("id:12345");
		// [3]添加需要回显得内容
		// query.addField("id");
		// query.addField("name");
		// query.setRows(20);//设置每页显示多少条

		QueryResponse response = client.query(query,METHOD.POST);

		SolrDocumentList list = response.getResults();

		for (SolrDocument solr : list) {
			System.out.println(solr.getFieldValue("id"));
			System.out.println(solr.getFieldValue("name"));
		}

		client.close();

	}

}
