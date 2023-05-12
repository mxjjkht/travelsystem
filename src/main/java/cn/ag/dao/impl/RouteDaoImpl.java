package cn.ag.dao.impl;

import cn.ag.dao.RouteDao;
import cn.ag.domain.Route;
import cn.ag.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private  JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 查询总记录数
     * @param cid
     * @return
     */
    @Override
    public int findtotalcount(int cid,String rname) {
        /*String sql="select count(*) from tab_route where  cid = ? ";*/
       /* Integer totalcount = jdbcTemplate.queryForObject(sql, Integer.class, cid);*/
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");
            params.add(cid);//添加？对应的值
        }

        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
     *查询route路线集合
     */
    @Override
    public List<Route> findroute(int cid, int start, int PageSize,String rname) {
    /*    String sql = "select * from tab_route where cid = ? limit ? , ?";//select * from table LIMIT 0,5//limit 10,10*/
      /*  List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class),cid,start,PageSize);*/
        //String sql = "select * from tab_route where cid = ? and rname like ?  limit ? , ?";
        //String sql = "select * from tab_route where cid = ? and rname like ?  limit ? , ?";
        String sql = " select * from tab_route where 1=1";
       // 1.定义sql模板
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");
            params.add(cid);//添加？对应的值
        }

        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件

        sql = sb.toString();

        params.add(start);
        params.add(PageSize);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    //index part2 人气+推荐+最新+主题
    /**
     * 首页人气旅游展示
     * @return
     */
    @Override
    public List<Route> findPopularRoute(int count) {
        String sql= null;
        try {
//            sql = "SELECT rid,COUNT(rid) AS COUNT FROM tab_favorite GROUP BY rid ORDER BY COUNT(rid) DESC  LIMIT 0, ?";
            sql = "SELECT * FROM tab_route ORDER BY rcount DESC  LIMIT 0,?;";
        } catch (Exception e) {
        }
        return  jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),count);
    }
    /**
     * 首页推荐旅游展示
     * @return
     */
    @Override
    public List<Route> findRecommendRoute(int count) {
        String sql= null;
        try {
//            sql = "SELECT rid,COUNT(rid) AS COUNT FROM tab_favorite GROUP BY rid ORDER BY COUNT(rid) DESC  LIMIT 0, ?";
            sql = "SELECT * FROM tab_route ORDER BY rcount DESC  LIMIT 0,?;";
        } catch (Exception e) {
        }
        return  jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),count);
    }
    /**
     * 首页最新旅游的展示
     * @param count
     * @return
     */
    @Override
    public List<Route> findindexNewRoute(int count) {
        String sql= null;
        try {
            sql = "SELECT * from tab_route  ORDER BY  rdate DESC  LIMIT 0,?;";
        } catch (Exception e) {
        }
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),count);
    }
    /**
     * 首页主题旅游展示
     * @param count
     * @return
     */
    public List<Route> findindexThemeRoute(int count) {
        String sql=null;
        try {
            sql = "SELECT * from tab_route WHERE isThemeTour=1 LIMIT 0,?;";
        } catch (Exception e) {

        }
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),count);
    }

    //index part3 国内+国外
    /**
     * 首页旅游路线国内游
     * @return
     */
    @Override
    public List<Route> findrouteindex() {
        String sql="select * from tab_route where cid=5 order by rid LIMIT 6;";
        List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return routes;
    }
    /**
     * 首页旅游展示国外游
     * @return
     */
    public List<Route> findrouteindexguowai() {
        String sql="select * from tab_route where cid=4 order by rid LIMIT 6;";
        List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return routes;
    }
    /**
     * 首页旅游展示 推荐
     * @return
     */
    public List<Route> findrouteindexrecommend() {
        String sql="select * from tab_route where cid=4 order by rid LIMIT 6;";
        List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return routes;
    }

    /**
     * 详情页面的展示
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        String sql="select * from tab_route where rid = ?;";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }


    /**
     * 收藏排行榜查询分页的总记录数
     * @param rname
     * @param first
     * @param last
     * @return
     */
    @Override
    public int findfavoriterankcount(String rname, int first, int last) {
     //   String sql="SELECT count(*) FROM (SELECT * FROM (SELECT rid,COUNT(rid) AS COUNT FROM tab_favorite GROUP BY rid ORDER BY COUNT(rid) DESC)AS aa)AS bb,tab_route t WHERE t.rid = bb.rid";
//        String sql="select count(*) from tab_route order by count DESC where 1=1";
        String sql="select count(*) from tab_route  where 1=1  ";
        StringBuilder sb = new StringBuilder();
        List params= new ArrayList();

        if(rname!=null&rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
         if(first!=0){
             sb.append(" and price > ? ");
             params.add(first);
         }
        if(last!=0){
            sb.append(" and price < ?");
            params.add(last);
        }
          sql+=sb.toString();

        Integer totalcount = jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
        return totalcount;
    }
    /**
     * 收藏排行榜的线路展示
     * @param rname
     * @param first
     * @param last
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> favoriterank(String rname, int first, int last, int start, int pageSize) {
     //   String sql="SELECT * FROM (SELECT * FROM (SELECT rid,COUNT(rid) AS COUNT FROM tab_favorite GROUP BY rid ORDER BY COUNT(rid) DESC)AS aa)AS bb,tab_route t WHERE t.rid = bb.rid";
        String sql="select * from tab_route  where 1=1 ";
        StringBuilder sb = new StringBuilder();

        List params= new ArrayList();
        if(rname!=null&rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        if(first!=0){
            sb.append(" and price > ? ");
            params.add(first);
        }
        if(last!=0){
            sb.append(" and price < ?");
            params.add(last);
        }
        sb.append(" limit ? , ? ");//分页条件

       sql+= sb.toString();
        params.add(start);
        params.add(pageSize);
        List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return  routes;
    }


    /**
     * 查询所有路线数量
     * @param rname
     * @return
     */
    @Override
    public int findtotalcount1(String rname) {
        String sql = "select count(*) from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }
    /**
     * 查询所有路线
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findroute1(int start, int pageSize, String rname) {
        String sql = " select * from tab_route where 1 = 1 ";
        // 1.定义sql模板
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件

        sql = sb.toString();

        params.add(start);
        params.add(pageSize);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

}
