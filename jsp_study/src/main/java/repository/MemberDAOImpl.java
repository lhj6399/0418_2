package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import orm.DatabaseBuilder;

public class MemberDAOImpl implements MemberDAO {
	//로그객체
	private static final Logger log = LoggerFactory.getLogger(MemberDAOImpl.class);
	
	//sqlSession객체
	private SqlSession sql;
	
	public MemberDAOImpl(){
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}
	//namespace="MemberMapper"  MemberMapper.id

	@Override
	public int insert(MemberVO mvo) {
		log.info("join dao in");
		int isOk = sql.insert("MemberMapper.add", mvo);
		if(isOk>0) {sql.commit();}
		return isOk;
	}

	@Override
	public MemberVO getID(MemberVO mvo) {
		log.info("login dao in");
		return sql.selectOne("MemberMapper.login", mvo);
	}

	@Override
	public int lastLogin(String id) {
		log.info("lastLogin dao in");
		int isOk = sql.update("MemberMapper.last", id);
		if(isOk>0) {sql.commit();}
		return isOk;
	}

	@Override
	public List<MemberVO> getList() {
		log.info("list dao in");
		return sql.selectList("MemberMapper.list");
	}

	@Override
	public int update(MemberVO mvo) {
		log.info("update dao in");
		//insert, update, delete  => commit 이 없으면 성공은 하지만 반영이 안됨.
		int isOk = sql.update("MemberMapper.up", mvo);
		if(isOk>0) { sql.commit();}
		return isOk;
	}

	@Override
	public int delete(String id) {
		log.info("delete dao in");
		int isOk =sql.delete("MemberMapper.del", id);
		if(isOk>0) { sql.commit();}
		return isOk;
	}
	
	
	
}
