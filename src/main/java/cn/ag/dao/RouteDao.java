package cn.ag.dao;

import cn.ag.domain.Route;

import java.util.List;

public interface RouteDao {

    /**** 查询总记录数
     */
    public int findtotalcount(int cid,String rname);

    /****查询route路线集合
     */
    public List<Route> findroute(int cid,int start,int PageSize,String rname);

    /***index part2 人气+最新+推荐+主题
     * @param count
     * @return
     */
    List<Route> findPopularRoute(int count);
    List<Route> findRecommendRoute(int count);
    List<Route> findindexNewRoute(int count);
    List<Route> findindexThemeRoute(int count);

    //index part3 国内+国外+推荐（？）
    public  List<Route> findrouteindex();
    public  List<Route> findrouteindexguowai();
    public  List<Route> findrouteindexrecommend();

    //详情页
    public  Route findOne(int rid);

    //收藏排行榜
    int findfavoriterankcount(String rname, int first, int last);
    List<Route> favoriterank( String rname, int first, int last, int start, int pageSize);

    //总查询（cid=8)
    int findtotalcount1(String rname);
    List<Route> findroute1(int start, int pageSize, String rname);
}
