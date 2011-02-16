/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by CTA Group
 */
package org.guohai.android.cta.utility;

import org.guohai.android.cta.R;

import android.content.Context;

public class ErrorInfoParse {

	/**
	 * 通过状态返回相应语言的错误信息
	 * @param context
	 * @param state
	 * @return
	 */
	public static String GetErrorMessage(Context context,int state){
		switch(state){
		case -1001:
			return context.getString(R.string.error_info_post_error);
		case -1002:
			return context.getString(R.string.error_info_coordinate_parse_failure);
		case -1003:
			return context.getString(R.string.error_info_send_message_failure);
		default:
			return context.getString(R.string.error_info_unknown_error);
		}
	
	}
}
