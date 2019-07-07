package com.xmyy.common.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: zlp
 * Date: 2018/5/17 0026
 * Time: 15:20
 */
public class ToolsUtil {
    private static final String mobilePatterns = "^1\\d{10}$";
    private static final String mobilesPatterns = "^1\\d{10}(,1\\d{10})*$"; //验证是否以逗号隔开的手机号码
    private static final String emailPatterns = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	private static final String pwdPatterns = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$"; //6-16位，数字与字母组合
    
    private static final Pattern MOBILE = Pattern.compile(mobilePatterns);
    private static final Pattern MOBILES = Pattern.compile(mobilesPatterns);
    private static final Pattern EMAIL = Pattern.compile(emailPatterns);

    public static final boolean isMobiles(String mobilesStr) {
        return MOBILES.matcher(mobilesStr).matches();
    }
    public static final boolean isMobile(String mobileStr) {
        return MOBILE.matcher(mobileStr).matches();
    }

    public static final boolean isEmail(String emailStr) {
        return EMAIL.matcher(emailStr).matches();
    }

	public static final boolean isYooyopwd(String pwd) {
		return (pwd.matches(pwdPatterns));
	}
    public static final boolean isIdCardNumber(String idCardNumber) {
        // TODO
        return false;
    }

	/**
	 * 得到网页中图片的地址
	 */
	public static Set<String> getImgStr(String htmlStr, Integer end) {
		Set<String> pics = new HashSet<>();
		String img = "";
		Pattern p_image;
		Matcher m_image;
		//     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);

		while (m_image.find()) {
			if(end != null && end == 0){
				break;
			}
			// 得到<img />数据
			img = m_image.group();
			// 匹配<img>中的src数据
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
			if(end != null) end = end - 1;
		}
		return pics;
	}



	public static void main(String[] args) {
		/*System.out.println("3.4545458 >>"+ToolsUtil.isYooyopwd("3.4545458"));
		System.out.println("12345 >>"+ToolsUtil.isYooyopwd("12345"));
		System.out.println("123456 >>"+ToolsUtil.isYooyopwd("123456"));
		System.out.println("asf12 >>"+ToolsUtil.isYooyopwd("asf12"));
		System.out.println("afdaff >>"+ToolsUtil.isYooyopwd("sfasfa"));

		System.out.println("@@#@#! >>"+ToolsUtil.isYooyopwd("@@#@#!"));
		System.out.println("~123wer >>"+ToolsUtil.isYooyopwd("~123wer"));

		System.out.println("afd1233 >>"+ToolsUtil.isYooyopwd("afd1233"));
		System.out.println("afd12331234 >>"+ToolsUtil.isYooyopwd("afd12331234"));

		System.out.println("afd12331234eeeeeeeeeeeeee111 >>"+ToolsUtil.isYooyopwd("afd12331234eeeeeeeeeeeeee111"));
		System.out.println("afd1233@FREDWW >>"+ToolsUtil.isYooyopwd("afd1233@FREDWW"));*/

		ToolsUtil.getImgStr("<p>鹅泉位于县城西南6公里处，鹅泉又名灵泉，是靖西著名的八景之一，已有七百多年的历史，闻名天下的&ldquo;鹅泉跃鲤三层浪&rdquo;引起无数中外游客的神往，明代皇帝赐封的&ldquo;灵泉晚照&rdquo;古石刻使鹅泉披上了神秘的色彩。</p><p><img src=\"//fimg0.yooyoimg.com//00/34/rBWAUloub2-AN0ikAAY68sENaNU449.jpg\" /></p><p>鹅泉常年涌水不断，水质清澈，泉中盛产鲤鱼、青竹鱼等，件肥味美。</p><div>&nbsp;</div><div>&nbsp;</div><p><img src=\"//fimg0.yooyoimg.com//00/31/rBWAU1oub5OALd37AAXfj3WfV7U035.jpg\" /></p><p>杨媪庙&mdash;&mdash;位于鹅泉中央小岛上，座南朝北。庙旁立有三块巨大石碑，岛上树木葱茂，环境幽雅，有一条水泥桥与岸边相通，方便游人进入岛内游览。</p><p><img src=\"//fimg0.yooyoimg.com//00/31/rBWAVFoucBCAHOiiAAau3AtOk2k613.jpg\" /></p><p>古桥&mdash;&mdash;古桥是鹅泉风景区的&mdash;个胜景，古桥建于清朝年间，桥长约60米，宽1.5米，全为石灰料石组成，是由15个石拱桥组成，长桥卧波，景色优美，气势壮观，鹅泉古桥为靖西县保留得比较完整的古桥。</p><div>&nbsp;</div><div>&nbsp;</div><p><img src=\"//fimg0.yooyoimg.com//00/34/rBWAUVoubmyAbgssAAUM8J5KIxo371.jpg\" /></p>",3);
	}

}
