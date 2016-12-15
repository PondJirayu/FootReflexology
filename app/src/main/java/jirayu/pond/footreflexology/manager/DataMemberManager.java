package jirayu.pond.footreflexology.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import jirayu.pond.footreflexology.dao.MemberItemDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DataMemberManager {

    private static DataMemberManager instance;

    public static DataMemberManager getInstance() {
        if (instance == null)
            instance = new DataMemberManager();
        return instance;
    }

    private Context mContext;
    private MemberItemDao memberItemDao;

    public MemberItemDao getMemberItemDao() {
        return memberItemDao;
    }

    public void setMemberItemDao(MemberItemDao memberItemDao) {
        this.memberItemDao = memberItemDao;
    }

}
