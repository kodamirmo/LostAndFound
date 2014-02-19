package com.blastic.lostandfound.services;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.util.Log;

import com.blastic.lostandfound.transferobjects.Report;

public class ReportsByPageWebService {

	private HttpClient cliente;

	public ReportsByPageWebService() {
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		cliente = new DefaultHttpClient(params);
	}

	private JSONObject consultarWebService(int page) {
		String url = "http://pawhub.me/api/lnf/Reports/page/"+page;

		HttpGet get = new HttpGet(url);

		HttpResponse respuesta = null;

		try {
			respuesta = cliente.execute(get);
		} catch (ClientProtocolException e) {
			Log.e("Tag", "Error en Protocolo en cliente");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			Log.e("Tag", "Error IO");
			e.printStackTrace();
			return null;
		}

		String response = null;

		try {
			response = EntityUtils.toString(respuesta.getEntity());
		} catch (ParseException e) {
			Log.e("Tag", "Error en parser de respuesta");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			Log.e("Tag", "Error IO en respuesta");
			e.printStackTrace();
			return null;
		}
		if (response == null) {
			Log.i("TAG", "La respuesta es null");
			return null;
		}

		// Log.i("TAG", "La respuesta de es =" + response);

		JSONObject res = null;

		try {
			res = new JSONObject(response);
		} catch (JSONException e) {
			Log.e("Tag", "Error en parser JSON");
			e.printStackTrace();
			return null;
		}

		return res;

	}

	public ArrayList<Report> getReports(int page) {

		ArrayList<Report> listReport = new ArrayList<Report>();
		JSONObject json = consultarWebService(page);

		JSONArray array = null;

		try {
			array = json.getJSONArray("result");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		int size = array.length();
		for (int i = 0; i < size; i++) {
			JSONObject jsonPadre = null;
			try {
				jsonPadre = array.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
			listReport.add(parserAReporte(jsonPadre));
		}
		return listReport;

	}

	public Report crearReporte(int page) {
		JSONObject json = consultarWebService(page);
		return parser(json);
	}

	private Report parser(JSONObject json) {
		JSONArray array = null;
		try {
			array = json.getJSONArray("result");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		JSONObject jsonPadre = null;
		try {
			jsonPadre = array.getJSONObject(0);
		} catch (JSONException e) {

			e.printStackTrace();
			return null;
		}
		return parserAReporte(jsonPadre);
	}

	private Report parserAReporte(JSONObject jsonPadre) {

		String idReporte = null;
		try {
			idReporte = jsonPadre.getString("_id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.v("TAG", "ID REPORTE : " + idReporte);

		String userId = null;

		try {
			userId = jsonPadre.getString("_userId");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "userId : " + userId);

		String description = null;

		try {
			description = jsonPadre.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "description : " + description);

		String picture = null;

		try {
			picture = jsonPadre.getString("picture");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "picture : " + picture);

		String linkedTo = null;

		try {
			linkedTo = jsonPadre.getString("linkedTo");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "linkedTo : " + linkedTo);

		String reportCode = null;

		try {
			reportCode = jsonPadre.getString("reportCode");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "reportCode : " + reportCode);

		String sharedCount = null;

		try {
			sharedCount = jsonPadre.getString("sharedCount");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "sharedCount : " + sharedCount);

		String solved = null;

		try {
			solved = jsonPadre.getString("solved");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "solved : " + solved);

		String viewBy = null;

		try {
			viewBy = jsonPadre.getString("viewBy");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "viewBy : " + viewBy);

		String date = null;

		try {
			date = jsonPadre.getString("date");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "date : " + date);

		String comments = null;

		try {
			comments = jsonPadre.getString("comments");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "comments : " + comments);

		String alertTo = null;

		try {
			alertTo = jsonPadre.getString("alertTo");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "alertTo : " + alertTo);

		String location = null;

		try {
			location = jsonPadre.getString("location");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "location : " + location);

		String detail = null;

		try {
			detail = jsonPadre.getString("detail");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		Log.v("TAG", "detail : " + detail);

		/*
		 * JSONObject jsonMessages = null; try { jsonMessages =
		 * jsonPadre.getJSONObject("messages"); } catch (JSONException e) {
		 * e.printStackTrace(); return null; }
		 * 
		 * 
		 * String messages = null;
		 * 
		 * try { messages = jsonMessages.getString("messages"); } catch
		 * (JSONException e) { e.printStackTrace(); return null; }
		 * 
		 * Log.v("TAG", "messages : " + messages);
		 * 
		 * String errors = null;
		 * 
		 * try { errors = jsonMessages.getString("errors"); } catch
		 * (JSONException e) { e.printStackTrace(); return null; }
		 * 
		 * Log.v("TAG", "errors : " + errors);
		 */

		final Report auxReport = new Report(idReporte, Report.CAUSE_ABUSE, 1, location, picture,
				comments, true, 40, false, userId);

		return auxReport;

	}
}
