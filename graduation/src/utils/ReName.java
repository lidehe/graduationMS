package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
public class ReName {  
	private String ip; 
	public ReName() {
	} 
	public ReName(String ip){
		this.ip=ip;//����ip��ַ   
	}  
		/*    * ���ʱ���   */
	public String getTimeStamp(){
			String temp=null;
			SimpleDateFormat  sdf=new  SimpleDateFormat("yyyyMMddHHmmssSSS");
			temp=sdf.format(new Date());
			return temp;  
	}  
		/*    * ��ʱ��������λ�����   */   
	public String getIPTimeStampRand(){
			//������    
			StringBuffer buf=new StringBuffer();   
			if(ip!=null){    
				String str[]=this.ip.split("\\.");    
				for(int i=0;i<str.length;i++){     
					buf.append(this.addZero(str[i],3));     
				}    
			}    
			buf.append(this.getTimeStamp());   
			Random rand=new Random();   
			for(int i=0;i<3;i++){    
				buf.append(rand.nextInt(10));   
			}    
			return buf.toString();   
	}  /*   * ��0   */   
	public String addZero(String str,int len){   
			StringBuffer s=new StringBuffer();   
			s.append(str);   
			while(s.length()<len){    
				s.insert(0, "0");   
			}    
			return s.toString();   
	} 
}