package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.VideoDao;
import model.Video;
import java.io.IOException;
import java.util.List;

@WebServlet({"/home", "/home/index"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDao videoDao = new VideoDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Mặc định ban đầu ở trang 1 nếu người dùng không truyền tham số ?page=
        int pageNumber = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                pageNumber = Integer.parseInt(pageParam);
                if (pageNumber < 1) pageNumber = 1;
            } catch (NumberFormatException e) {
                pageNumber = 1;
            }
        }

        // Gọi DAO lấy chính xác 6 tiểu phẩm có lượt xem cao nhất cho trang hiện tại
        List<Video> listVideos = videoDao.findTop6ByViews(pageNumber);
        
        // Đẩy dữ liệu ra giao diện JSP hiển thị cho khách hàng
        request.setAttribute("videos", listVideos);
        request.setAttribute("currentPage", pageNumber);
        
        request.getRequestDispatcher("/views/user/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}