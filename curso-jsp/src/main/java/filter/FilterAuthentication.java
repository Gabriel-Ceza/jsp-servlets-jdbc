package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAuthentication extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public FilterAuthentication() {
        super();
    }

	/*encerra os processos quando o servidor é parado*/
	/*ex: terminaria os processos de conexão com o banco*/
	public void destroy() {
	}

	/*intercepta as requisições e respostas do sistema*/
	/*tudo o que fizer no sistema vai passar por aqui*/
	/*ex: validação de autenticação
	 Dar commit e rollback no banco
	 validar e fazer redirecionamento de paginas*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String usuarioLogado = (String) session.getAttribute("user");
		String urlParaAutenticar = req.getServletPath();
		
		if(usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")){
			RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
			request.setAttribute("msg", "Efetue o login!");
			redireciona.forward(request, response);
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

	/*inicia os processos quando o servidor sobe*/
	/*ex: incia conexão com o banco*/
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
