package br.com.pe.urbana.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.icu.util.StringTokenizer;

public class Filtro implements Filter {

	private ArrayList<String> contextsList;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String [] arrContext = {"inicio", "consultaCartao", "consultaCPF", "cadastroUsuario", "vinculacao", "consultaCadastro"};
		ArrayList<String> lArray = new ArrayList<String>();

		for(String context: arrContext){
			lArray.add(context);
		}
		
		addHeader(response);

		boolean allowedRequest = false;
		String context = getContext(request);

		if (contextsList.contains(context)) {
			allowedRequest = true;
		}
		if(lArray.contains(context)){
			allowedRequest = true;
		}
		if (allowedRequest) {
			chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher("/inicio").forward(request, response);
		}
		
	}
	
	private String getContext(ServletRequest request) {
		String context = "";

		String servletPath = ((HttpServletRequest) request).getServletPath();
		StringTokenizer st = new StringTokenizer(servletPath, "/");
		if (st.hasMoreTokens()) {
			context = st.nextToken();
		}
		return context;
	
	}
	
	private void addHeader(ServletResponse response) {
		
//		 Esse header a "solução" encontrada para resolver um problema similar
//		 ao relatado em :
//		 http://adamyoung.net/IE-Blocking-iFrame-Cookies?page=1

		((HttpServletResponse) response)
			.addHeader("P3P", "CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

		String urls = config.getInitParameter("avoid-context");
		StringTokenizer token = new StringTokenizer(urls, ",");

		contextsList = new ArrayList<String>();

		while (token.hasMoreTokens()) {
			contextsList.add(token.nextToken());
		}
	}

}
