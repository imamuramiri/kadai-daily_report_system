package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Employee;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String context_path = ((HttpServletRequest)request).getContextPath();
        String servlet_path = ((HttpServletRequest)request).getServletPath();

    if(!servlet_path.matches("/css.*")) {
        //フィルタをかけないため
        HttpSession session = ((HttpServletRequest)request).getSession();
      //セッションスコープから従業員情報取り出し、eに格納(ログアウト時、セッションスコープに従業員情報が格納されていないのでeはnull)
        Employee e = (Employee)session.getAttribute("login_employee");

        //login以外のページにアクセスすると、ログイン状態でないなら/loginに戻る
        if(!servlet_path.equals("/login")){
            if(e==null){
((HttpServletResponse)response).sendRedirect(context_path + "/login");
                return;
            }
            //従業員管理のページ/employeesにアクセスすると、ログインしている従業員情報が一般か管理かをチェック
            if(servlet_path.matches("/employees.*") && e.getAdmin_flag() == 0) {
                //0ならトップページへ戻る設定をしている
                ((HttpServletResponse)response).sendRedirect(context_path + "/");
                return;
        }
            //ログイン後、トップページへいくようにする
     } else {

         if(e != null) {
             ((HttpServletResponse)response).sendRedirect(context_path + "/");
             return;
     }
     }
    }
    chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
