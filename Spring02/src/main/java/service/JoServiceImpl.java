package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapperInterface.JoMapper;
import vo.JoVO;

@Service
public class JoServiceImpl implements JoService {
	
	@Autowired
	JoMapper mapper;
	
	
	@Override
	public List<JoVO> selectList() {
		return mapper.selectList();
	} // selectList
	
	@Override
	public JoVO selectOne(JoVO vo) {
		return mapper.selectOne(vo);
	} // selectOne
	
	@Override
	public int insert(JoVO vo) {
		return mapper.insert(vo);
	} // insert
	
	@Override
	public int update(JoVO vo) {
		return mapper.update(vo);
	} // update
	
	@Override
	public int delete(JoVO vo) {
		return mapper.delete(vo);
	} // delete
	

} // class
