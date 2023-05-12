package cn.ag.service;

import cn.ag.domain.PageBean;
import cn.ag.domain.Route;

import java.util.List;

public interface RouteService {
    public  PageBean<Route> pagebean(int cid, int currentPage, int PageSize,String rname);
    //index part2 胡桃精选的模块 推荐+人气+最新+主题
    List<Route> findPopularRoute(int count);
    List<Route> findRecommendRoute(int count);
    List<Route> findindexNewRoute(int count);
    List<Route> findindexThemeRoute(int count);

    //index part3 国内游+境外游
    public List<Route> findrouteindex();
    public List<Route> findrouteindexguowai();
    public List<Route> findrouteindexrecommend();

    //根据rid找到route内容
    Route findOne(int rid);

    //收藏路线排行榜
    PageBean<Route> favoriterank(int currentPage, String rname, int first, int last, int pageSize);

    //查询所有路线
    PageBean<Route> pagebean1(int currentPage, int pageSize, String rname);
}
