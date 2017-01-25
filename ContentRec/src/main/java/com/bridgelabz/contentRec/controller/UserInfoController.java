/*
* @Author:Dhareppa Metri
* File:UserInfoController.java
* Purpose:Controller class for to store user information.
**/
package com.bridgelabz.contentRec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.contentRec.model.GameInfo;
import com.bridgelabz.contentRec.model.UserInfo;
import com.bridgelabz.contentRec.model.VisitorsInfo;
import com.bridgelabz.contentRec.services.GameInfoService;
import com.bridgelabz.contentRec.services.UserInfoService;
import com.bridgelabz.contentRec.services.VisitorsInfoService;

@Controller
public class UserInfoController {

	@Autowired
	VisitorsInfoService mVisitorsInfoService;

	@Autowired
	GameInfoService mGameInfoService;

	@Autowired
	UserInfoService mUserInfoService;

	@RequestMapping(value = "/storeUserInfo", method = RequestMethod.GET)
	public String dispalyGameInfo() {
		return "StoreUserInfo";
	}

	@RequestMapping(value = "/storeUserDatatoDb", method = RequestMethod.POST)
	public String storeUserDatatoDb(@RequestParam("contentId") String parContentId) {
		// Multiple visitors information for same contentId
		List<VisitorsInfo> lVisitorsInfo = mVisitorsInfoService.getVisitorsInfoByContentId(parContentId);
		// Game information
		List<GameInfo> lGameInfo = mGameInfoService.getGameInfoByContentId(parContentId);
		for (GameInfo lGameInfoObj : lGameInfo) {
			UserInfo lUserInfo = new UserInfo();
			for (VisitorsInfo lVisitorsInfoObj : lVisitorsInfo) {

				lUserInfo.setmUserId(lVisitorsInfoObj.getmVisitorId());
				lUserInfo.setmContentId(lVisitorsInfoObj.getmContentId());
				lUserInfo.setmContentName(lVisitorsInfoObj.getmContentName());
				lUserInfo.setmCategoryName(lGameInfoObj.getmCategoryName());
				lUserInfo.setmTag(lGameInfoObj.getmMetaTags());
				lUserInfo.setmFileSize(lGameInfoObj.getmFileSize());
				lUserInfo.setmGroupId(lGameInfoObj.getmGroupId());
				lUserInfo.setmView(lVisitorsInfoObj.getmView());
				lUserInfo.setmDownload(lVisitorsInfoObj.getmDownload());
				mUserInfoService.saveUserInfo(lUserInfo);

			}

		}

		return "StoreUserInfo";
	}

	@RequestMapping(value = "/getUserHistory", method = RequestMethod.GET)
	public String getUserHistory() {
		return "GetUserHistory";
	}

	@RequestMapping(value = "/getUserHistory", method = RequestMethod.POST)
	public ModelAndView getHistory(@RequestParam("visitorId") String parVisitorId) {
		List<UserInfo> lUserInfo = mUserInfoService.getVisitorHistoryByVisitorId(parVisitorId);
		return new ModelAndView("DisplayUserHistory", "userInfo", lUserInfo);
	}
}