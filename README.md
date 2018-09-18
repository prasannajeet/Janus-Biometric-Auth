# Fingertip - Now in Kotlin!
[![CircleCI](https://circleci.com/gh/prasannajeet/Fingertip/tree/master.svg?style=svg)](https://circleci.com/gh/prasannajeet/Fingertip/tree/master) [![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip?ref=badge_shield)

Fingertip - The easy fingerprint authentication library for Android
### Currently in alpha (v0.2) - may be buggy

This Android library project is designed to simplify the complicated fingerprint authentication process in Android.

It offers 3 types of Fingerprint authentication mechanisms

- Fingerprint Authentication Dialog (both pre and post SDK 28 versions) (Implementation Complete, post SDK 28 version UI pending)
- Device lock based Authentication (will work only if fingerprint available) (In Progress)
- Fingerprint Authentication Activity (TBD)

There are 2 steps in conducting the authentication
1. Set configuration such as context and type of authentication (dialog/activity/device lock) 
2. Call authentication API and listen for results

**Sample Code:** In order to add a fingerprint dialog fragment and recieve results in a callback below is the sample code:
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

For now the UI will be the default UI provided by the library, eventually override options will be provided

### Development Milestones
- **[v0.2.0](https://github.com/prasannajeet/Fingertip/releases/tag/v0.2.0)** - Fragment based fingerprint authentication implemented with initial UI *(September 15 2018)*
- **v0.1.3** - Converted project to Kotlin *(September 15 2018)*
- **v0.1.2** - Initial Commit *(August 9 2018)*


## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip?ref=badge_large)
