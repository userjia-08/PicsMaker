package com.example.picsmaker.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.picsmaker.domain.Picture;



public class Picturedao {
	
	public static String sql;
	
	//�ӷ���������ͼƬ�ͱ�ǩ��ϵ���Զ����룬�������ݿ����
	public static boolean setRelationship(Picture pic){
		//TODO ������ת����sql�������
		//ͼƬ·��
		String path = pic.getPath();
		//��ǩ
		List<String> tags = pic.getTags();
		
		//�ӷ��������صĽ��Ӧ����ֻ��һ����ǩ
		String tag = tags.get(0);
		int indexofpic = getPictureID(path);
		
		if(indexofpic==-1) {
			//����ͼƬ��
			String sql1 = "insert into PICTURE(Picture_path)VALUES('"+path+"')";
			SQLiteJDBC.insertDB(sql1);
			indexofpic = getPictureID(path);
		}
		//�����ݿ���Ҹ�ͼƬ�ͱ�ǩ��Ӧ��Picture_id��Tag_id
		
		int indexoftag = getTagID(tag);
		if(indexoftag==-1) {
			//����ͼƬ��
			String sql1 = "insert into TAG(Tag_name)VALUES('"+tag+"')";
			SQLiteJDBC.insertDB(sql1);
			indexoftag = getTagID(tag);
		}
		int pic_id,tag_id;
		pic_id = indexofpic;
		tag_id = indexoftag;
		
		//����ͼƬ-��ǩ��ϵ
		String sql2 = "insert into RELATIONSHIP(Picture_id, Tag_id)VALUES('"+pic_id+"', '"+tag_id+"')";
	
		try {
			
			SQLiteJDBC.insertDB(sql2);
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	public static void update(Picture pic){
		
		//TODO ������ת����sql�������
	}
	
	//�����Ӧ��ǩ�������ǩ��������ɾ��tag��������tag�µ�����ͼƬ���ñ�ǩͼƬ��ϵ������ɾ��
	public static boolean removeTag(String name){
		
		sql = "Delete from TAG, RELATIONSHIP where Tag_name = '"+name+"' AND TAG.Tag_id = RELATIONSHIP.Tag_id;";
		if(SQLiteJDBC.updateDB(sql)) {
			return true;
		}else {
			return false;
		}
	}
	
	//�����ĳ��ǩ�µģ�ĳͼƬ��ɾ��ͼƬ ����ǩ��ϵ//ֻ�Ǵ����ݿ�ɾ����ͼƬ��ϵ����
	public static boolean removePics(Picture pic){
		String tag_name = pic.getTags().get(0);
		String pic_path = pic.getPath();
		
		sql = "Delete from PICTURE, RELATIONSHIP where Picture_path = '"+pic_path+"' AND PICTURE.Picture_id = RELATIONSHIP.Picture_id ;";
		
		if(SQLiteJDBC.updateDB(sql)) {
			return true;
		}else {
			return false;
		}
	}
	
	//TODO ���ݱ�ǩ��ѯ����Ҫ���ͼƬ�б�
	public static List<String> getPicsByTag(String tag){
	
		int tag_id = getTagID(tag);
		sql = "select Picture_path from TAG, RELATIONSHIP, PICTURE where TAG.Tag_id = RELATIONSHIP.Tag_id AND RELATIONSHIP.Picture_id = PICTURE.Picture_id AND TAG.Tag_id = '"+tag_id+"' ;";
		try {
			//�õ�path---	
			List<Map<Object, Object>> pathlist = SQLiteJDBC.select(sql);
			List<String> result = new ArrayList<String>();
			//����ÿ���������map��ȡ��path
			for(int i=0; i<pathlist.size(); i++) {
				String p = (String) pathlist.get(i).get("Picture_path");
				result.add(p);
			}
			return result;
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	//��ȡ���б�ǩ��չʾ
	public static List<String> getTags(){
		
		sql = "select Tag_name from TAG";
		
		try {
			//�õ��������б�ǩ��
			List<Map<Object, Object>> taglist = SQLiteJDBC.select(sql);
			List<String> result = new ArrayList<String>();
			//����ÿ���������map��ȡ��path
			for(int i=0; i<taglist.size(); i++) {
				String p = (String) taglist.get(i).get("Tag_name");
				result.add(p);
			}
			return result;
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	//��ȡͼƬid
	public static int getPictureID(String path) {
		
		sql = "select Picture_id from PICTURE where Picture_path = '"+path+"';";
		List<Map<Object, Object>> idlist = SQLiteJDBC.select(sql);
		if(idlist.size()>0) {
			int id = (Integer) idlist.get(0).get("Picture_id");
			return id;
		}
		return -1;
	}
	
	//��ȡtag id
	public static int getTagID(String name) {
		
		sql = "select Tag_id from TAG where Tag_name = '"+name+"' ;";
		List<Map<Object, Object>> idlist = SQLiteJDBC.select(sql);
		if(idlist.size()>0) {
			int id = (Integer) idlist.get(0).get("Tag_id");
			return id;
		}
		return -1;
	}
	
	//��ĳ��ǩ�£������ѡ������һ��ͼƬ
	public static boolean addPicByTag(Picture pic) {
		 
		if(setRelationship(pic)) {
			return true;
		}
		return false;
	}
	
	//�½���ǩ
	public static boolean addTag(String tag) {
		sql = "insert into TAG(Tag_name)VALUES('"+tag+"')";
		if(SQLiteJDBC.insertDB(sql)) {
			return true;
		}
		return false;
	}
	
}
