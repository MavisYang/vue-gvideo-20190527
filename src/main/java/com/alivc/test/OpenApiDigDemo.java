package com.alivc.test;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * 本例子只是说明调用的流程。本demo只是展示一个调用过程，没有考虑线程安全，并发性能这些情况。因为每个开发者使用的环境和依赖包不同，实现也不同，请自行处理。
 *     要点：
 *        1、要自己保存好调用登陆接口之后的用户信息，包含cookie，公钥。 
 *        2、注意除了登陆接口之外的接口调用时的加密方式
 *        3、请求接口后注意判断state值，true才说明调用成功。
 *        4、注意加密和解密的的最大长度，要支持分段加密解密
 *        
 *        
 */
/** 
 * ClassName: OpenApiDigDemo <br/> 
 * Function: TODO OpenApiDigDemo util. <br/> 
 * Reason:   TODO OpenApiDigDemo. <br/> 
 * Date:     2019年3月16日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
public class OpenApiDigDemo {
	//这个是开发环境，正式环境：https://openapi.dmhmusic.com
	private final static String OPEN_API_URL = ""; 
	//这个q_source只是用于本例，开发时请联系产品负责人分配一个。有开发和正式两个环境之分。
	private final static String Q_SOURCE = ""; 
	
	public static void main(String[] args){
		//示例一：http组件自动维护cookie
		testCase1();
		
		//示例二：模拟自己维护cookie，请自行实现一个与自己所使用的框架相符合的实现方式，本例只是一个模拟实现。
		//testCase2();
	}
	
	//示例一，http组件自动维护cookie
	private static void testCase1(){
		CloseableHttpClient httpClient = httpClient();
		//登陆取得公钥以及身份信息，有效期内调用一次就可以了
		String[] loginResult = dologin(httpClient);
		String publicKey = loginResult[0];
		System.out.println("--公钥--\n" + publicKey);
		//todo -- save public key 调用成功，要自己保存好公钥，有要求加密解密的地方就使用这个公钥
		
		//下面如果搜索没有结果，请联系产品负责人查看你们有使用权利的歌曲
		Map<String, String> params = new HashMap<String, String>(2);
		/*params.put("artistName", "刘德华");//没有结果时请自己调整下这个关键词
		params.put("trackTitle", "冰雨");
		params.put("searchType", "1");*/
		params.put("pageNo", "1");
		params.put("pageSize", "10");
		//String[] result = doRequst(httpClient, null, "/SEARCH/inSearch.json", params, publicKey);
		String[] result = doRequst(httpClient, null, "/TRACKLIST/songGetHot.json", params, publicKey);
		System.out.println("--结果--\n" + result[0]);
		
	}
	
	//示例二，自己维护cookie，cookie有多种维护方式，自己实现就行。
	private static void testCase2(){
		CloseableHttpClient httpClient = httpClient();
		//登陆取得公钥以及身份信息，有效期内调用一次就可以了
		String[] loginResult = dologin(httpClient);
		String publicKey = loginResult[0];
		String cookie = loginResult[1];
		System.out.println("--公钥--\n" + publicKey);
		System.out.println("--cookie--\n" + cookie);
		//todo -- save public key 调用成功，要自己保存好公钥，有要求加密解密的地方就使用这个公钥
		//todo -- save cookie 调用成功，要自己保存好cookie
		
		httpClient = httpClient();
		//下面如果搜索没有结果，请联系产品负责人查看你们有使用权利的歌曲
		Map<String, String> params = new HashMap<String, String>(5);
		//没有结果时请自己调整下这个关键词
		params.put("artistName", "Synthetic Pulse");
		params.put("trackTitle", "");
		params.put("searchType", "1");
		params.put("pageNo", "1");
		params.put("pageSize", "10");
		String[] result = doRequst(httpClient, cookie, "/SEARCH/inSearch.json", params, publicKey);
		System.out.println("--结果--\n" + result[0]);
	}
	
	/**
	 * 登录并获取cookie身份以及公钥
	 *    注意，有效期7天，过期再次调用这个接口进行身份验证
	 */
	private static String[] dologin(CloseableHttpClient httpClient){
		String url = OPEN_API_URL + "/OPENAPI/openApiLogin.json";
		HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        NameValuePair pair = new BasicNameValuePair("q_source", Q_SOURCE);
        pairs.add(pair);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, Consts.UTF_8));
        String[] response = doRequest(httpClient, httpPost);
		if (response != null && response.length > 0) {
			JSONObject json = JSON.parseObject(response[0]);
			Boolean bo = json.getBoolean("state");
			//判断调用是否成功
			if (bo) {
				String publicKey = json.getString("data");
				if (StringUtils.isNotBlank(publicKey)) {
					//注意下面代码的替换字符处理
					publicKey = publicKey.replace("-----BEGIN PUBLIC KEY-----\n", "").replace("\n-----END PUBLIC KEY-----\n", "");
					response[0] = publicKey;
					return response;
				}
			}
		}
		return null;
	}
	/**
	 * 通用的接口请求加密处理
	 * @param _params    接口文档规定的具体参数
	 * @param action     要请求的接口地址
	 * @param publicKey  公钥
	 */
	private static String[] doRequst(CloseableHttpClient httpClient, String cookie, String action, Map<String, String> params, String publicKey){
		if (params == null) {
			params = new HashMap<String, String>(1);
		}
		params.put("action", action);
		String q = null;
		try {
			//对参数进行加密
			String json = JSON.toJSONString(params);
			byte[] digJson = encryptByPublicKey(publicKey, json.getBytes("UTF-8"));
			q = Base64.encodeBase64String(digJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//统一请求的地址
		String url = OPEN_API_URL + "/auth/";
		//设置加密后的参数
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        NameValuePair pair = new BasicNameValuePair("q", q);
        pairs.add(pair);
        //发起请求
		HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, Consts.UTF_8));
        if (cookie != null && cookie.length() > 0) {
            httpPost.setHeader("cookie", cookie);
		}
		String[] response = doRequest(httpClient, httpPost);
		return response;
	}
    private static String[] doRequest(CloseableHttpClient httpClient, HttpRequestBase httpRequestBase) {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = httpClient.execute(httpRequestBase);
            entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_OK:
                    String result = EntityUtils.toString(entity, Consts.UTF_8);
                    String setCookie = "";
                    Header head = response.getFirstHeader("Set-Cookie");
                    if (head != null) {
                    	setCookie = head.getValue();
					}
                    return new String[]{result, setCookie};
                default:
                	System.out.println("Http resquest failed, code=" + statusCode);
            }
        }catch (Exception e) {
        	e.printStackTrace();
		} finally {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (Exception e) {
                	e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                	e.printStackTrace();
                }
            }
            if(httpRequestBase != null){
                try {
                    httpRequestBase.releaseConnection();
                } catch (Exception e) {
                	e.printStackTrace();
                }
            }
        }
        return null;
    }
	private static CloseableHttpClient httpClient(){
    	SSLConnectionSocketFactory sslsf = null;
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", sslsf)
                .build();
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        httpClientConnectionManager.setMaxTotal(300);
        httpClientConnectionManager.setDefaultMaxPerRoute(300);
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
        httpClientConnectionManager.setDefaultConnectionConfig(connectionConfig);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
        		.setSSLSocketFactory(sslsf)
                .setConnectionManager(httpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        return httpClient;
    }
    
    private static final String KEY_ALGORITHM = "RSA";
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;
	/**
	 * 公钥加密过程
	 */
	private static byte[] encryptByPublicKey(String publicKey, byte[] content) {
		if (publicKey == null || publicKey.trim().length() == 0 || content == null || content.length == 0) {
			return null;
		}
		RSAPublicKey rsaPublicKey = loadPublicKey(publicKey);
		return encryptByPublicKey(rsaPublicKey, content);
	}
	/**
	 * 公钥加密过程
	 */
	private static byte[] encryptByPublicKey(RSAPublicKey publicKey, byte[] content) {
		if (publicKey == null || content == null || content.length == 0) {
			return null;
		}
		if (content.length <= MAX_ENCRYPT_BLOCK) {
			try {
				Cipher cipher = getCipher();
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				byte[] output = cipher.doFinal(content);
				return output;
			} catch (Exception e) {
	        	e.printStackTrace();
				return null;
			}
		}else{
			return encryptByPublicKeySubsection(publicKey, content);
		}
	}
	//分段公钥加密过程
	private static byte[] encryptByPublicKeySubsection(RSAPublicKey publicKey, byte[] content) {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			int inputLen = content.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        int maxLeng = MAX_ENCRYPT_BLOCK - 1;
	        for(int i = 0; inputLen - offSet > 0; offSet = i * maxLeng) {
	            byte[] cache;
	            if(inputLen - offSet > maxLeng) {
	                cache = cipher.doFinal(content, offSet, maxLeng);
	            } else {
	                cache = cipher.doFinal(content, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            ++i;
	        }
	        byte[] output = out.toByteArray();
	        out.close();
	        return output;
		} catch (Exception e) {
        	e.printStackTrace();
		}
		return null;
	}
	/**
	 * 公钥解密过程
	 */
	protected static byte[] decryptByPublicKey(String publicKey, byte[] content) {
		if (StringUtils.isBlank(publicKey) || content == null || content.length == 0) {
			return null;
		}
		RSAPublicKey rsaPublicKey = loadPublicKey(publicKey);
		return decryptByPublicKey(rsaPublicKey, content);
	}
	/**
	 * 公钥解密过程
	 */
	private static byte[] decryptByPublicKey(RSAPublicKey publicKey, byte[] content) {
		if (publicKey == null || content == null || content.length == 0) {
			return null;
		}
		if (content.length <= MAX_DECRYPT_BLOCK) {
			try {
				Cipher cipher = getCipher();
				cipher.init(Cipher.DECRYPT_MODE, publicKey);
				byte[] output = cipher.doFinal(content);
				return output;
			} catch (Exception e) {
	        	e.printStackTrace();
			}
		}else{
			return decryptByPublicKeySubsection(publicKey, content);
		}
		return null;
	}
	//分段公钥解密过程
	private static byte[] decryptByPublicKeySubsection(RSAPublicKey publicKey, byte[] content) {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			int inputLen = content.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        for(int i = 0; inputLen - offSet > 0; offSet = i * MAX_DECRYPT_BLOCK) {
	            byte[] cache;
	            if(inputLen - offSet > MAX_DECRYPT_BLOCK) {
	                cache = cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK);
	            } else {
	                cache = cipher.doFinal(content, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            ++i;
	        }
	        byte[] output = out.toByteArray();
	        out.close();
	        return output;
		} catch (Exception e) {
        	e.printStackTrace();
		}
		return null;
    }
	/**
	 * 从字符串中加载公钥
	 */
	private static RSAPublicKey loadPublicKey(String publicKeyStr) {
		if (StringUtils.isBlank(publicKeyStr)) {
			return null;
		}
		try {
			byte[] buffer = Base64.decodeBase64(publicKeyStr);
			KeyFactory keyFactory = getKeyFactory();
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
        	e.printStackTrace();
		}
		return null;
	}
	//线程安全以及并发、性能问题自行处理
	private static KeyFactory keyFactory;
	private static KeyFactory getKeyFactory() {
		if (keyFactory == null) {
			synchronized (KEY_ALGORITHM) {
				if (keyFactory == null) {
					try {
						keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
					} catch (Exception e) {
			        	e.printStackTrace();
					}
				}
			}
		}
		return keyFactory;
	}
	//线程安全以及并发、性能问题自行处理
	private static Cipher cipher;
	private static Cipher getCipher() {
		if (cipher == null) {
			synchronized (KEY_ALGORITHM) {
				if (cipher == null) {
					try {
						cipher = Cipher.getInstance(KEY_ALGORITHM);
					} catch (Exception e) {
			        	e.printStackTrace();
					}
				}
			}
		}
		return cipher;
	}
    
}

