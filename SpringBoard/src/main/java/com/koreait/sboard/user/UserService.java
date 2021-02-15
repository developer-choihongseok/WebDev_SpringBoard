package com.koreait.sboard.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.common.FileUtils;
import com.koreait.sboard.common.MailUtils;
import com.koreait.sboard.common.SecurityUtils;
import com.koreait.sboard.model.AuthDTO;
import com.koreait.sboard.model.AuthEntity;
import com.koreait.sboard.model.UserEntity;
import com.koreait.sboard.model.UserImgEntity;

@Service
public class UserService {
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private MailUtils mailUtils;
	
	@Autowired
	private FileUtils fUtils;
	
	@Autowired
	private HttpSession hs;
	
	public UserEntity selUser(UserEntity param) {
		return mapper.selUser(param);
	}
	
	// 1 : 로그인 성공, 2 : 아이디 없음, 3 : 비밀번호 틀림
	public int login(UserEntity param, HttpSession hs) {
		UserEntity dbData = selUser(param);
		
		if(dbData == null) {
			return 2;	// 아이디 없음
		}
		
		String cryptLoginPw = SecurityUtils.hashPassword(param.getUser_pw(), dbData.getSalt());
		
		if(!cryptLoginPw.equals(dbData.getUser_pw())) {
			return 3;	// 비밀번호 틀림
		}
		
		dbData.setSalt(null);
		dbData.setUser_pw(null);
		
		hs.setAttribute(Const.KEY_LOGINUSER, dbData);
		
		return 1;	// 로그인 성공
	}
	
	public int insUser(UserEntity param) {
		String salt = SecurityUtils.getsalt();
		String encryptPw = SecurityUtils.hashPassword(param.getUser_pw(), salt);
		
		param.setSalt(salt);
		param.setUser_pw(encryptPw);

		return mapper.insUser(param);
	}
	
	// 0 : 메일전송 실패, 1 : 성공, 2 : 아이디 확인
	public int findPwProc(AuthEntity param) {
		// 이메일 주소 얻어오기
		UserEntity param2 = new UserEntity();
		param2.setUser_id(param.getUser_id());
		
		UserEntity vo = selUser(param2);
				
		if(vo == null) {
			return 0;
		}
		
		String email = vo.getEmail();
		
		String code = SecurityUtils.getPrivateCode(10);
		System.out.println("code : " + code);
		
		mapper.delAuth(param);	// 일단 삭제
		
		param.setCd(code);
		mapper.insAuth(param);
		
		System.out.println("email : " + email);
		
		return mailUtils.sendFindPwEmail(email, param.getUser_id(), code);
	}
	
	// 비밀번호를 변경
	public int findPwAuthProc(AuthDTO param) {
		// cd, user_id 확인 작업
		AuthEntity ae = mapper.selAuth(param);
		
		if(ae == null) {
			return 0;	// 아이디 없음
		} else if(ae.getRest_sec() > Const.AUTH_REST_SEC) {
			return 2;	// 인증시간 초과
		}
		
		// 비밀번호 암호화
		String salt = SecurityUtils.getsalt();
		String encryptPw = SecurityUtils.hashPassword(param.getUser_pw(), salt);
		
		UserEntity param2 = new UserEntity();
		param2.setUser_id(param.getUser_id());
		param2.setUser_pw(encryptPw);
		param2.setSalt(salt);
		
		return mapper.updUser(param2);
	}
	
	// 이미지 업로드
	public int profileUpload(MultipartFile[] imgs, HttpSession hs) {
		int i_user = SecurityUtils.getLoginUserPK(hs);
		
		// 로그인이 되어있지 않거나 업로드한 이미지가 없을 경우..(즉, 미리 에러가 날 수 있는 가능성을 잡아내고 시작을 한다.)
		if(i_user < 1 || imgs.length == 0) {
			return 0;
		}
		
		String folder = "/resources/img/user/" + i_user;
		
		try {
			for(int i = 0; i < imgs.length; i++) {
				MultipartFile file = imgs[i];
				
				String fileNm = fUtils.saveFile(file, folder);
				
				if(fileNm == null) {
					return 0;
				}
				
				// 메인 이미지 업데이트
				if(i == 0) {
					UserEntity param = new UserEntity();
					param.setI_user(i_user);
					param.setProfile_img(fileNm);
					
					mapper.updUser(param);
				}
				
				UserImgEntity param2 = new UserImgEntity();
				param2.setI_user(i_user);
				param2.setImg(fileNm);
				
				mapper.insertUserImg(param2);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public List<UserImgEntity> selUserImgList(UserEntity param){
		int i_user = SecurityUtils.getLoginUserPK(hs);
		param.setI_user(i_user);
		
		return mapper.selUserImgList(param);
	}
}
