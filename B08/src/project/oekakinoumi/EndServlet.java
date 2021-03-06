package project.oekakinoumi;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/project/oekakinoumi/end")
public class EndServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		ServletContext context = this.getServletContext();
		Chat chat = (Chat) context.getAttribute("chat");
		Canvas canvas = (Canvas) context.getAttribute("canvas");
		UserManager userManager = (UserManager) context.getAttribute("usermanager");
		if (chat == null) {
			chat = new Chat();
			context.setAttribute("chat", chat);
		}
		if (canvas == null){
			canvas = new Canvas();
			context.setAttribute("canvas", canvas);
		}
		if (userManager == null){
			userManager = new UserManager();
			context.setAttribute("usermanager", userManager);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		UserManager userManager = (UserManager) context.getAttribute("usermanager");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//ユーザーを保持しているなら
		if (user != null) {
			session.invalidate();
			userManager.removeUser(user);
			User sys = new User();
			sys.setName("");
			Statement statement = new Statement();
			Chat chat = (Chat) context.getAttribute("chat");
			statement.setUser(sys);
			statement.setMessage(user.getName()+"さんが退室しました");
			chat.addStatement(statement);
		}
		response.sendRedirect("index.html");
	}

}
