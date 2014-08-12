package com.anderscore.simpleandroidchat;

import javax.security.auth.callback.CallbackHandler;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.provided.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.util.Base64;
import org.jivesoftware.smack.util.ByteUtils;

/**
 * Implementation of the SASL-PLAIN authentication mechanism
 * @author Ralf Bommersbach
 *
 */
public class SASLPlainMechanism extends SASLMechanism {

	public static final String NAME = PLAIN;
	
	private static final String INITAL_NONCE = "00000001";
	
	@Override
	protected void authenticateInternal(CallbackHandler arg0)
			throws SmackException {
		throw new UnsupportedOperationException("CallbackHandler not (yet) supported");
	}

	@Override
	protected String getAuthenticationText() throws SmackException {
		// concatenate and encode username (authcid) and password
		byte[] authcid = ("\u0000" + this.authenticationId).getBytes();
		byte[] passw = ("\u0000" + this.password).getBytes();
		
		return Base64.encodeBytes(ByteUtils.concact(authcid, passw));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	protected SASLMechanism newInstance() {
		return new SASLPlainMechanism();
	}

}
