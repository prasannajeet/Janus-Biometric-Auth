package com.praszapps.fingertip.model.repository;

import android.app.KeyguardManager;
import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import com.praszapps.fingertip.MVP.FingertipMVPContract;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.inject.Inject;

public class FingertipRepository extends FingerprintManagerCompat.AuthenticationCallback implements FingertipMVPContract.IFingerprintRepository {

    private String KEY_NAME = "";
    private FingerprintManagerCompat mFingerprintManager;
    private CancellationSignal mSignal = null;
    @Inject
    Context mContext;
    private KeyguardManager mKeyguardManager = null;
    private KeyStore mKeyStore = null;
    private KeyGenerator mKeyGenerator = null;
    private Cipher mCipher = null;
    private FingerprintManagerCompat.CryptoObject mCryptoObj = null;

    @Override
    public void initalize() {
        mFingerprintManager = FingerprintManagerCompat.from(mContext);
        mSignal = new CancellationSignal();
        mKeyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);

        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");

            mKeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            mKeyGenerator.init( new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            mKeyGenerator.generateKey();
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME,
                    null);
            mCipher.init(Cipher.ENCRYPT_MODE, key);

            mCryptoObj = new FingerprintManagerCompat.CryptoObject(mCipher);

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }


    }

    public boolean isDeviceFingerprintAuthReady() {
        return mFingerprintManager != null && mKeyguardManager != null && mFingerprintManager.isHardwareDetected() && mFingerprintManager.hasEnrolledFingerprints() && mKeyguardManager.isDeviceSecure();
    }

    @Override
    public void startFingerprintTracking() {

    }

    @Override
    public void stopFingerprintTracking() {

    }

    @Override
    public boolean isFingerprintAvailable() {
        return mFingerprintManager != null && mFingerprintManager.isHardwareDetected() && mFingerprintManager.hasEnrolledFingerprints();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
    }
}
