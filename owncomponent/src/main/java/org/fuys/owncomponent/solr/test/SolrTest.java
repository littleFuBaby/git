package org.fuys.owncomponent.solr.test;

import java.io.IOException;
import java.util.Random;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrTest {

	private static final String SOLR_URL = "http://47.100.18.54:8708/solr/sxyp_core";

	public static HttpSolrClient getClient() {
		return new HttpSolrClient.Builder(SOLR_URL).withConnectionTimeout(1000).withSocketTimeout(10000).build();
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

		QueryResponse response = client.query(query);

		SolrDocumentList list = response.getResults();

		for (SolrDocument solr : list) {
			System.out.println(solr.getFieldValue("id"));
			System.out.println(solr.getFieldValue("name"));
		}

		client.close();

	}

}
