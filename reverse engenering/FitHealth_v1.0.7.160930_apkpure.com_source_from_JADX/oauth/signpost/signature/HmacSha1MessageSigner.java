package oauth.signpost.signature;

import com.weibo.net.HttpHeaderFactory;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;

public class HmacSha1MessageSigner extends OAuthMessageSigner {
    private static final String MAC_NAME = "HmacSHA1";

    public String getSignatureMethod() {
        return HttpHeaderFactory.CONST_SIGNATURE_METHOD;
    }

    public String sign(HttpRequest request, HttpParameters requestParams) throws OAuthMessageSignerException {
        try {
            SecretKey key = new SecretKeySpec((OAuth.percentEncode(getConsumerSecret()) + '&' + OAuth.percentEncode(getTokenSecret())).getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            String sbs = new SignatureBaseString(request, requestParams).generate();
            OAuth.debugOut("SBS", sbs);
            return base64Encode(mac.doFinal(sbs.getBytes("UTF-8"))).trim();
        } catch (Exception e) {
            throw new OAuthMessageSignerException(e);
        } catch (Exception e2) {
            throw new OAuthMessageSignerException(e2);
        }
    }
}
