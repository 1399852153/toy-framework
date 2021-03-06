import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author xiongyx
 * @Create 2018/4/11.
 */

@WebServlet("/hello")
public class HelloServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet hello");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String currentTime = dateFormat.format(new Date());

        request.setAttribute("currentTime",currentTime);
        request.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(request,response);
    }
}