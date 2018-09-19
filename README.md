# Fingertip - The easy fingerprint authentication library for Android
[![CircleCI](https://circleci.com/gh/prasannajeet/Fingertip/tree/master.svg?style=svg)](https://circleci.com/gh/prasannajeet/Fingertip/tree/master) [![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip?ref=badge_shield)  [ ![Download](https://api.bintray.com/packages/prasannajeet89/praszappsMaven/fingertip/images/download.svg) ](https://bintray.com/prasannajeet89/praszappsMaven/fingertip/_latestVersion)

### Currently in alpha (v0.2) - may be buggy

Tired of having to write an entire chunk of code, check for OS versionscreate layouts and store icons to implement a simple fingerprint authentication dialog? 

This Android library project is designed to simplify the complicated fingerprint authentication process in Android.

Upon completion, it will offer 3 types of Fingerprint authentication mechanisms

- Fingerprint Authentication Dialog (both pre and post SDK 28 versions) (Implementation Complete, post SDK 28 version UI pending)
- Device lock based Authentication (will work only if fingerprint available) (In Progress)
- Fingerprint Authentication Activity (TBD)

There are 2 steps in conducting the authentication
1. Set configuration such as context and type of authentication (dialog/activity/device lock) 
2. Call authentication API and listen for results

## Gradle Dependency
In your project level build.gradle add the following maven url
```
maven { url "https://dl.bintray.com/prasannajeet89/praszappsMaven" }
```
In you module level build.gradle add the following dependency
```
implementation 'com.praszapps.fingertip:fingertip:0.2.2'
```

## Sample Code
**Kotlin**
```
val config = FingertipAuthConfig(this, AuthenticationStyle.FINGERPRINT_DIALOG)
FingertipAuthenticator.INSTANCE.doFingerprintAuthentication(config, object : FingertipAuthenticationResult {
   override fun onFingertipAuthSuccess() {
     Toast.makeText(this@MainActivity, "Successful Auth", Toast.LENGTH_LONG).show()
   }
   override fun onFingertipAuthFailed(errorType: FingertipErrorType) {
     when(errorType) {
      is FingertipErrorType.DeviceApiLevelBelow23 -> {
         Log.e("MainActivity", "FailureReason: API level is below 23 for device")
         Toast.makeText(this@MainActivity, "FailureReason: API level is below 23 for device", Toast.LENGTH_LONG).show()
      }
      is FingertipErrorType.ErrorDuringFingerprintAuthentication -> {
         Log.e("MainActivity", "Failure reason: ${errorType.message}")
         Toast.makeText(this@MainActivity, "Auth failed with reason - ${errorType.message}", Toast.LENGTH_LONG).show()
      }
    }
  }
})
```
**Java**
```
FingertipAuthConfig config = new FingertipAuthConfig(MainActivity.this, AuthenticationStyle.FINGERPRINT_DIALOG);
   FingertipAuthenticator.INSTANCE.doFingerprintAuthentication(config, new FingertipAuthenticationResult() {
      @Override
      public void onFingertipAuthSuccess() {
         Toast.makeText(MainActivity.this, "Successful Auth", Toast.LENGTH_LONG).show()
      }

      @Override
      public void onFingertipAuthFailed(@NonNull FingertipErrorType fingertipErrorType) {
         if(fingertipErrorType instanceof FingertipErrorType.DeviceApiLevelBelow23) {
            Log.e("MainActivity", "FailureReason: API level is below 23 for device")
            Toast.makeText(MainActivity.this, "FailureReason: API level is below 23 for device", Toast.LENGTH_SHORT).show();
         } else if(fingertipErrorType instanceof FingertipErrorType.ErrorDuringFingerprintAuthentication) {
            Log.e("MainActivity", "Failure reason: "+((FingertipErrorType.ErrorDuringFingerprintAuthentication) fingertipErrorType).getMessage());
            Toast.makeText(MainActivity.this, ((FingertipErrorType.ErrorDuringFingerprintAuthentication) fingertipErrorType).getMessage(), Toast.LENGTH_SHORT).show();
         }
      }
});
```

For now the UI will be the default UI provided by the library, eventually override options will be provided

## Release Notes
Release notes can be found [here](https://github.com/prasannajeet/Fingertip/blob/master/release-notes.md)

## Development Milestones
- **v0.2.2** - Stabilized fingerprint dialog. Added coroutines. Removed RxJava. Configured proguard. *(September 18 2018)*
- **v0.2.0** - Fragment based fingerprint authentication implemented with initial UI *(September 15 2018)*
- **v0.1.3** - Converted project to Kotlin *(September 15 2018)*
- **v0.1.2** - Initial Commit *(August 9 2018)*


## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip?ref=badge_large)
