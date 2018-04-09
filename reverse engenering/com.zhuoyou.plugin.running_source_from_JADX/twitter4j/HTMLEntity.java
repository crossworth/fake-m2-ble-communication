package twitter4j;

import android.support.v4.media.TransportMediator;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.baidu.location.BDLocation;
import com.droi.btlib.service.BluetoothManager;
import com.droi.btlib.service.BtManagerService;
import com.umeng.facebook.internal.FacebookRequestErrorClassification;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

final class HTMLEntity {
    private static final Map<String, String> entityEscapeMap = new HashMap();
    private static final Map<String, String> escapeEntityMap = new HashMap();

    HTMLEntity() {
    }

    static String escape(String original) {
        StringBuilder buf = new StringBuilder(original);
        escape(buf);
        return buf.toString();
    }

    static void escape(StringBuilder original) {
        int index = 0;
        while (index < original.length()) {
            String escaped = (String) entityEscapeMap.get(original.substring(index, index + 1));
            if (escaped != null) {
                original.replace(index, index + 1, escaped);
                index += escaped.length();
            } else {
                index++;
            }
        }
    }

    static String unescape(String original) {
        if (original == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder(original);
        unescape(buf);
        return buf.toString();
    }

    static void unescape(StringBuilder original) {
        int index = 0;
        while (index < original.length()) {
            index = original.indexOf("&", index);
            if (-1 != index) {
                int semicolonIndex = original.indexOf(";", index);
                if (-1 != semicolonIndex) {
                    String entity = (String) escapeEntityMap.get(original.substring(index, semicolonIndex + 1));
                    if (entity != null) {
                        original.replace(index, semicolonIndex + 1, entity);
                    }
                    index++;
                } else {
                    return;
                }
            }
            return;
        }
    }

    static String unescapeAndSlideEntityIncdices(String text, UserMentionEntity[] userMentionEntities, URLEntity[] urlEntities, HashtagEntity[] hashtagEntities, MediaEntity[] mediaEntities) {
        int i;
        int entityIndexesLength = ((0 + (userMentionEntities == null ? 0 : userMentionEntities.length)) + (urlEntities == null ? 0 : urlEntities.length)) + (hashtagEntities == null ? 0 : hashtagEntities.length);
        if (mediaEntities == null) {
            i = 0;
        } else {
            i = mediaEntities.length;
        }
        EntityIndex[] entityIndexes = new EntityIndex[(entityIndexesLength + i)];
        int copyStartIndex = 0;
        if (userMentionEntities != null) {
            System.arraycopy(userMentionEntities, 0, entityIndexes, 0, userMentionEntities.length);
            copyStartIndex = 0 + userMentionEntities.length;
        }
        if (urlEntities != null) {
            System.arraycopy(urlEntities, 0, entityIndexes, copyStartIndex, urlEntities.length);
            copyStartIndex += urlEntities.length;
        }
        if (hashtagEntities != null) {
            System.arraycopy(hashtagEntities, 0, entityIndexes, copyStartIndex, hashtagEntities.length);
            copyStartIndex += hashtagEntities.length;
        }
        if (mediaEntities != null) {
            System.arraycopy(mediaEntities, 0, entityIndexes, copyStartIndex, mediaEntities.length);
        }
        Arrays.sort(entityIndexes);
        boolean handlingStart = true;
        int entityIndex = 0;
        int delta = 0;
        StringBuilder unescaped = new StringBuilder(text.length());
        int i2 = 0;
        while (i2 < text.length()) {
            char c = text.charAt(i2);
            if (c == '&') {
                int semicolonIndex = text.indexOf(";", i2);
                if (-1 != semicolonIndex) {
                    String escaped = text.substring(i2, semicolonIndex + 1);
                    String entity = (String) escapeEntityMap.get(escaped);
                    if (entity != null) {
                        unescaped.append(entity);
                        i2 = semicolonIndex;
                        delta = 1 - escaped.length();
                    } else {
                        unescaped.append(c);
                    }
                } else {
                    unescaped.append(c);
                }
            } else {
                unescaped.append(c);
            }
            if (entityIndex < entityIndexes.length) {
                if (handlingStart) {
                    if (entityIndexes[entityIndex].getStart() == delta + i2) {
                        entityIndexes[entityIndex].setStart(unescaped.length() - 1);
                        handlingStart = false;
                    }
                } else if (entityIndexes[entityIndex].getEnd() == delta + i2) {
                    entityIndexes[entityIndex].setEnd(unescaped.length() - 1);
                    entityIndex++;
                    handlingStart = true;
                }
            }
            delta = 0;
            i2++;
        }
        if (entityIndex < entityIndexes.length && entityIndexes[entityIndex].getEnd() == text.length()) {
            entityIndexes[entityIndex].setEnd(unescaped.length());
        }
        return unescaped.toString();
    }

    static {
        String[][] entities = new String[251][];
        entities[0] = new String[]{"&nbsp;", "&#160;", " "};
        entities[1] = new String[]{"&iexcl;", "&#161;", "¡"};
        entities[2] = new String[]{"&cent;", "&#162;", "¢"};
        entities[3] = new String[]{"&pound;", "&#163;", "£"};
        entities[4] = new String[]{"&curren;", "&#164;", "¤"};
        entities[5] = new String[]{"&yen;", "&#165;", "¥"};
        entities[6] = new String[]{"&brvbar;", "&#166;", "¦"};
        entities[7] = new String[]{"&sect;", "&#167;", "§"};
        entities[8] = new String[]{"&uml;", "&#168;", "¨"};
        entities[9] = new String[]{"&copy;", "&#169;", "©"};
        entities[10] = new String[]{"&ordf;", "&#170;", "ª"};
        entities[11] = new String[]{"&laquo;", "&#171;", "«"};
        entities[12] = new String[]{"&not;", "&#172;", "¬"};
        entities[13] = new String[]{"&shy;", "&#173;", "­"};
        entities[14] = new String[]{"&reg;", "&#174;", "®"};
        entities[15] = new String[]{"&macr;", "&#175;", "¯"};
        entities[16] = new String[]{"&deg;", "&#176;", "°"};
        entities[17] = new String[]{"&plusmn;", "&#177;", "±"};
        entities[18] = new String[]{"&sup2;", "&#178;", "²"};
        entities[19] = new String[]{"&sup3;", "&#179;", "³"};
        entities[20] = new String[]{"&acute;", "&#180;", "´"};
        entities[21] = new String[]{"&micro;", "&#181;", "µ"};
        entities[22] = new String[]{"&para;", "&#182;", "¶"};
        entities[23] = new String[]{"&middot;", "&#183;", "·"};
        entities[24] = new String[]{"&cedil;", "&#184;", "¸"};
        entities[25] = new String[]{"&sup1;", "&#185;", "¹"};
        entities[26] = new String[]{"&ordm;", "&#186;", "º"};
        entities[27] = new String[]{"&raquo;", "&#187;", "»"};
        entities[28] = new String[]{"&frac14;", "&#188;", "¼"};
        entities[29] = new String[]{"&frac12;", "&#189;", "½"};
        entities[30] = new String[]{"&frac34;", "&#190;", "¾"};
        entities[31] = new String[]{"&iquest;", "&#191;", "¿"};
        entities[32] = new String[]{"&Agrave;", "&#192;", "À"};
        entities[33] = new String[]{"&Aacute;", "&#193;", "Á"};
        entities[34] = new String[]{"&Acirc;", "&#194;", "Â"};
        entities[35] = new String[]{"&Atilde;", "&#195;", "Ã"};
        entities[36] = new String[]{"&Auml;", "&#196;", "Ä"};
        entities[37] = new String[]{"&Aring;", "&#197;", "Å"};
        entities[38] = new String[]{"&AElig;", "&#198;", "Æ"};
        entities[39] = new String[]{"&Ccedil;", "&#199;", "Ç"};
        entities[40] = new String[]{"&Egrave;", "&#200;", "È"};
        entities[41] = new String[]{"&Eacute;", "&#201;", "É"};
        entities[42] = new String[]{"&Ecirc;", "&#202;", "Ê"};
        entities[43] = new String[]{"&Euml;", "&#203;", "Ë"};
        entities[44] = new String[]{"&Igrave;", "&#204;", "Ì"};
        entities[45] = new String[]{"&Iacute;", "&#205;", "Í"};
        entities[46] = new String[]{"&Icirc;", "&#206;", "Î"};
        entities[47] = new String[]{"&Iuml;", "&#207;", "Ï"};
        entities[48] = new String[]{"&ETH;", "&#208;", "Ð"};
        entities[49] = new String[]{"&Ntilde;", "&#209;", "Ñ"};
        entities[50] = new String[]{"&Ograve;", "&#210;", "Ò"};
        entities[51] = new String[]{"&Oacute;", "&#211;", "Ó"};
        entities[52] = new String[]{"&Ocirc;", "&#212;", "Ô"};
        entities[53] = new String[]{"&Otilde;", "&#213;", "Õ"};
        entities[54] = new String[]{"&Ouml;", "&#214;", "Ö"};
        entities[55] = new String[]{"&times;", "&#215;", "×"};
        entities[56] = new String[]{"&Oslash;", "&#216;", "Ø"};
        entities[57] = new String[]{"&Ugrave;", "&#217;", "Ù"};
        entities[58] = new String[]{"&Uacute;", "&#218;", "Ú"};
        entities[59] = new String[]{"&Ucirc;", "&#219;", "Û"};
        entities[60] = new String[]{"&Uuml;", "&#220;", "Ü"};
        entities[61] = new String[]{"&Yacute;", "&#221;", "Ý"};
        entities[62] = new String[]{"&THORN;", "&#222;", "Þ"};
        entities[63] = new String[]{"&szlig;", "&#223;", "ß"};
        entities[64] = new String[]{"&agrave;", "&#224;", "à"};
        entities[65] = new String[]{"&aacute;", "&#225;", "á"};
        entities[66] = new String[]{"&acirc;", "&#226;", "â"};
        entities[67] = new String[]{"&atilde;", "&#227;", "ã"};
        entities[68] = new String[]{"&auml;", "&#228;", "ä"};
        entities[69] = new String[]{"&aring;", "&#229;", "å"};
        entities[70] = new String[]{"&aelig;", "&#230;", "æ"};
        entities[71] = new String[]{"&ccedil;", "&#231;", "ç"};
        entities[72] = new String[]{"&egrave;", "&#232;", "è"};
        entities[73] = new String[]{"&eacute;", "&#233;", "é"};
        entities[74] = new String[]{"&ecirc;", "&#234;", "ê"};
        entities[75] = new String[]{"&euml;", "&#235;", "ë"};
        entities[76] = new String[]{"&igrave;", "&#236;", "ì"};
        entities[77] = new String[]{"&iacute;", "&#237;", "í"};
        entities[78] = new String[]{"&icirc;", "&#238;", "î"};
        entities[79] = new String[]{"&iuml;", "&#239;", "ï"};
        entities[80] = new String[]{"&eth;", "&#240;", "ð"};
        entities[81] = new String[]{"&ntilde;", "&#241;", "ñ"};
        entities[82] = new String[]{"&ograve;", "&#242;", "ò"};
        entities[83] = new String[]{"&oacute;", "&#243;", "ó"};
        entities[84] = new String[]{"&ocirc;", "&#244;", "ô"};
        entities[85] = new String[]{"&otilde;", "&#245;", "õ"};
        entities[86] = new String[]{"&ouml;", "&#246;", "ö"};
        entities[87] = new String[]{"&divide;", "&#247;", "÷"};
        entities[88] = new String[]{"&oslash;", "&#248;", "ø"};
        entities[89] = new String[]{"&ugrave;", "&#249;", "ù"};
        entities[90] = new String[]{"&uacute;", "&#250;", "ú"};
        entities[91] = new String[]{"&ucirc;", "&#251;", "û"};
        entities[92] = new String[]{"&uuml;", "&#252;", "ü"};
        entities[93] = new String[]{"&yacute;", "&#253;", "ý"};
        entities[94] = new String[]{"&thorn;", "&#254;", "þ"};
        entities[95] = new String[]{"&yuml;", "&#255;", "ÿ"};
        entities[96] = new String[]{"&fnof;", "&#402;", "ƒ"};
        entities[97] = new String[]{"&Alpha;", "&#913;", "Α"};
        entities[98] = new String[]{"&Beta;", "&#914;", "Β"};
        entities[99] = new String[]{"&Gamma;", "&#915;", "Γ"};
        entities[100] = new String[]{"&Delta;", "&#916;", "Δ"};
        entities[101] = new String[]{"&Epsilon;", "&#917;", "Ε"};
        entities[102] = new String[]{"&Zeta;", "&#918;", "Ζ"};
        entities[103] = new String[]{"&Eta;", "&#919;", "Η"};
        entities[104] = new String[]{"&Theta;", "&#920;", "Θ"};
        entities[105] = new String[]{"&Iota;", "&#921;", "Ι"};
        entities[106] = new String[]{"&Kappa;", "&#922;", "Κ"};
        entities[107] = new String[]{"&Lambda;", "&#923;", "Λ"};
        entities[108] = new String[]{"&Mu;", "&#924;", "Μ"};
        entities[109] = new String[]{"&Nu;", "&#925;", "Ν"};
        entities[110] = new String[]{"&Xi;", "&#926;", "Ξ"};
        entities[111] = new String[]{"&Omicron;", "&#927;", "Ο"};
        entities[BtManagerService.CLASSIC_CMD_GET_STEP] = new String[]{"&Pi;", "&#928;", "Π"};
        entities[BtManagerService.CLASSIC_SYNC_SPORT_DATA] = new String[]{"&Rho;", "&#929;", "Ρ"};
        entities[114] = new String[]{"&Sigma;", "&#931;", "Σ"};
        entities[115] = new String[]{"&Tau;", "&#932;", "Τ"};
        entities[BtManagerService.CLASSIC_SYNC_PERSONAL_DATA] = new String[]{"&Upsilon;", "&#933;", "Υ"};
        entities[117] = new String[]{"&Phi;", "&#934;", "Φ"};
        entities[118] = new String[]{"&Chi;", "&#935;", "Χ"};
        entities[119] = new String[]{"&Psi;", "&#936;", "Ψ"};
        entities[120] = new String[]{"&Omega;", "&#937;", "Ω"};
        entities[121] = new String[]{"&alpha;", "&#945;", "α"};
        entities[122] = new String[]{"&beta;", "&#946;", "β"};
        entities[123] = new String[]{"&gamma;", "&#947;", "γ"};
        entities[124] = new String[]{"&delta;", "&#948;", "δ"};
        entities[125] = new String[]{"&epsilon;", "&#949;", "ε"};
        entities[TransportMediator.KEYCODE_MEDIA_PLAY] = new String[]{"&zeta;", "&#950;", "ζ"};
        entities[TransportMediator.KEYCODE_MEDIA_PAUSE] = new String[]{"&eta;", "&#951;", "η"};
        entities[128] = new String[]{"&theta;", "&#952;", "θ"};
        entities[BtManagerService.CLASSIC_SYNC_SLEEP_MSG] = new String[]{"&iota;", "&#953;", "ι"};
        entities[TransportMediator.KEYCODE_MEDIA_RECORD] = new String[]{"&kappa;", "&#954;", "κ"};
        entities[131] = new String[]{"&lambda;", "&#955;", "λ"};
        entities[132] = new String[]{"&mu;", "&#956;", "μ"};
        entities[133] = new String[]{"&nu;", "&#957;", "ν"};
        entities[134] = new String[]{"&xi;", "&#958;", "ξ"};
        entities[135] = new String[]{"&omicron;", "&#959;", "ο"};
        entities[136] = new String[]{"&pi;", "&#960;", "π"};
        entities[137] = new String[]{"&rho;", "&#961;", "ρ"};
        entities[138] = new String[]{"&sigmaf;", "&#962;", "ς"};
        entities[139] = new String[]{"&sigma;", "&#963;", "σ"};
        entities[140] = new String[]{"&tau;", "&#964;", "τ"};
        entities[141] = new String[]{"&upsilon;", "&#965;", "υ"};
        entities[142] = new String[]{"&phi;", "&#966;", "φ"};
        entities[143] = new String[]{"&chi;", "&#967;", "χ"};
        entities[144] = new String[]{"&psi;", "&#968;", "ψ"};
        entities[145] = new String[]{"&omega;", "&#969;", "ω"};
        entities[146] = new String[]{"&thetasym;", "&#977;", "ϑ"};
        entities[147] = new String[]{"&upsih;", "&#978;", "ϒ"};
        entities[148] = new String[]{"&piv;", "&#982;", "ϖ"};
        entities[BtManagerService.CLASSIC_CMD_SET_ALARM] = new String[]{"&bull;", "&#8226;", "•"};
        entities[150] = new String[]{"&hellip;", "&#8230;", "…"};
        entities[151] = new String[]{"&prime;", "&#8242;", "′"};
        entities[152] = new String[]{"&Prime;", "&#8243;", "″"};
        entities[153] = new String[]{"&oline;", "&#8254;", "‾"};
        entities[154] = new String[]{"&frasl;", "&#8260;", "⁄"};
        entities[155] = new String[]{"&weierp;", "&#8472;", "℘"};
        entities[156] = new String[]{"&image;", "&#8465;", "ℑ"};
        entities[157] = new String[]{"&real;", "&#8476;", "ℜ"};
        entities[158] = new String[]{"&trade;", "&#8482;", "™"};
        entities[159] = new String[]{"&alefsym;", "&#8501;", "ℵ"};
        entities[160] = new String[]{"&larr;", "&#8592;", "←"};
        entities[161] = new String[]{"&uarr;", "&#8593;", "↑"};
        entities[162] = new String[]{"&rarr;", "&#8594;", "→"};
        entities[BtManagerService.CLASSIC_CMD_SET_MUSIC_LIST] = new String[]{"&darr;", "&#8595;", "↓"};
        entities[164] = new String[]{"&harr;", "&#8596;", "↔"};
        entities[165] = new String[]{"&crarr;", "&#8629;", "↵"};
        entities[166] = new String[]{"&lArr;", "&#8656;", "⇐"};
        entities[BDLocation.TypeServerError] = new String[]{"&uArr;", "&#8657;", "⇑"};
        entities[168] = new String[]{"&rArr;", "&#8658;", "⇒"};
        entities[169] = new String[]{"&dArr;", "&#8659;", "⇓"};
        entities[170] = new String[]{"&hArr;", "&#8660;", "⇔"};
        entities[171] = new String[]{"&forall;", "&#8704;", "∀"};
        entities[172] = new String[]{"&part;", "&#8706;", "∂"};
        entities[173] = new String[]{"&exist;", "&#8707;", "∃"};
        entities[174] = new String[]{"&empty;", "&#8709;", "∅"};
        entities[175] = new String[]{"&nabla;", "&#8711;", "∇"};
        entities[176] = new String[]{"&isin;", "&#8712;", "∈"};
        entities[177] = new String[]{"&notin;", "&#8713;", "∉"};
        entities[178] = new String[]{"&ni;", "&#8715;", "∋"};
        entities[179] = new String[]{"&prod;", "&#8719;", "∏"};
        entities[180] = new String[]{"&sum;", "&#8721;", "∑"};
        entities[181] = new String[]{"&minus;", "&#8722;", "−"};
        entities[182] = new String[]{"&lowast;", "&#8727;", "∗"};
        entities[183] = new String[]{"&radic;", "&#8730;", "√"};
        entities[184] = new String[]{"&prop;", "&#8733;", "∝"};
        entities[185] = new String[]{"&infin;", "&#8734;", "∞"};
        entities[186] = new String[]{"&ang;", "&#8736;", "∠"};
        entities[187] = new String[]{"&and;", "&#8743;", "∧"};
        entities[188] = new String[]{"&or;", "&#8744;", "∨"};
        entities[189] = new String[]{"&cap;", "&#8745;", "∩"};
        entities[FacebookRequestErrorClassification.EC_INVALID_TOKEN] = new String[]{"&cup;", "&#8746;", "∪"};
        entities[191] = new String[]{"&int;", "&#8747;", "∫"};
        entities[192] = new String[]{"&there4;", "&#8756;", "∴"};
        entities[193] = new String[]{"&sim;", "&#8764;", "∼"};
        entities[194] = new String[]{"&cong;", "&#8773;", "≅"};
        entities[195] = new String[]{"&asymp;", "&#8776;", "≈"};
        entities[196] = new String[]{"&ne;", "&#8800;", "≠"};
        entities[197] = new String[]{"&equiv;", "&#8801;", "≡"};
        entities[198] = new String[]{"&le;", "&#8804;", "≤"};
        entities[199] = new String[]{"&ge;", "&#8805;", "≥"};
        entities[200] = new String[]{"&sub;", "&#8834;", "⊂"};
        entities[201] = new String[]{"&sup;", "&#8835;", "⊃"};
        entities[202] = new String[]{"&sube;", "&#8838;", "⊆"};
        entities[203] = new String[]{"&supe;", "&#8839;", "⊇"};
        entities[204] = new String[]{"&oplus;", "&#8853;", "⊕"};
        entities[205] = new String[]{"&otimes;", "&#8855;", "⊗"};
        entities[206] = new String[]{"&perp;", "&#8869;", "⊥"};
        entities[207] = new String[]{"&sdot;", "&#8901;", "⋅"};
        entities[208] = new String[]{"&lceil;", "&#8968;", "⌈"};
        entities[209] = new String[]{"&rceil;", "&#8969;", "⌉"};
        entities[210] = new String[]{"&lfloor;", "&#8970;", "⌊"};
        entities[211] = new String[]{"&rfloor;", "&#8971;", "⌋"};
        entities[212] = new String[]{"&lang;", "&#9001;", "〈"};
        entities[213] = new String[]{"&rang;", "&#9002;", "〉"};
        entities[214] = new String[]{"&loz;", "&#9674;", "◊"};
        entities[215] = new String[]{"&spades;", "&#9824;", "♠"};
        entities[216] = new String[]{"&clubs;", "&#9827;", "♣"};
        entities[217] = new String[]{"&hearts;", "&#9829;", "♥"};
        entities[218] = new String[]{"&diams;", "&#9830;", "♦"};
        entities[219] = new String[]{"&quot;", "&#34;", "\""};
        entities[220] = new String[]{"&amp;", "&#38;", "&"};
        entities[221] = new String[]{"&lt;", "&#60;", "<"};
        entities[222] = new String[]{"&gt;", "&#62;", ">"};
        entities[223] = new String[]{"&OElig;", "&#338;", "Œ"};
        entities[224] = new String[]{"&oelig;", "&#339;", "œ"};
        entities[225] = new String[]{"&Scaron;", "&#352;", "Š"};
        entities[226] = new String[]{"&scaron;", "&#353;", "š"};
        entities[227] = new String[]{"&Yuml;", "&#376;", "Ÿ"};
        entities[228] = new String[]{"&circ;", "&#710;", "ˆ"};
        entities[229] = new String[]{"&tilde;", "&#732;", "˜"};
        entities[230] = new String[]{"&ensp;", "&#8194;", " "};
        entities[231] = new String[]{"&emsp;", "&#8195;", " "};
        entities[232] = new String[]{"&thinsp;", "&#8201;", " "};
        entities[233] = new String[]{"&zwnj;", "&#8204;", "‌"};
        entities[234] = new String[]{"&zwj;", "&#8205;", "‍"};
        entities[235] = new String[]{"&lrm;", "&#8206;", "‎"};
        entities[236] = new String[]{"&rlm;", "&#8207;", "‏"};
        entities[237] = new String[]{"&ndash;", "&#8211;", "–"};
        entities[238] = new String[]{"&mdash;", "&#8212;", "—"};
        entities[239] = new String[]{"&lsquo;", "&#8216;", "‘"};
        entities[240] = new String[]{"&rsquo;", "&#8217;", "’"};
        entities[BluetoothManager.TYPE_BT_CONNECTED] = new String[]{"&sbquo;", "&#8218;", "‚"};
        entities[BluetoothManager.TYPE_BT_CONNECTION_LOST] = new String[]{"&ldquo;", "&#8220;", "“"};
        entities[BluetoothManager.TYPE_DATA_SENT] = new String[]{"&rdquo;", "&#8221;", "”"};
        entities[BluetoothManager.TYPE_DATA_ARRIVE] = new String[]{"&bdquo;", "&#8222;", "„"};
        entities[BluetoothManager.TYPE_MAPCMD_ARRIVE] = new String[]{"&dagger;", "&#8224;", "†"};
        entities[BluetoothManager.TYPE_BT_CONNECTION_FAIL] = new String[]{"&Dagger;", "&#8225;", "‡"};
        entities[247] = new String[]{"&permil;", "&#8240;", "‰"};
        entities[248] = new String[]{"&lsaquo;", "&#8249;", "‹"};
        entities[249] = new String[]{"&rsaquo;", "&#8250;", "›"};
        entities[Callback.DEFAULT_SWIPE_ANIMATION_DURATION] = new String[]{"&euro;", "&#8364;", "€"};
        for (String[] entity : entities) {
            entityEscapeMap.put(entity[2], entity[0]);
            escapeEntityMap.put(entity[0], entity[2]);
            escapeEntityMap.put(entity[1], entity[2]);
        }
    }
}
