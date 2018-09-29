# Janus - The easy biometric authentication library for Android
[![GitLabCI](https://gitlab.com/prasannajeet/Janus/badges/master/build.svg)](https://gitlab.com/prasannajeet/Janus/pipelines) [![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgitlab.com%2Fprasannajeet%2FJanus.svg?type=shield)](https://app.fossa.io/projects/git%2Bgitlab.com%2Fprasannajeet%2FJanus?ref=badge_shield) [ ![Download](https://api.bintray.com/packages/prasannajeet89/praszappsMaven/janus/images/download.svg) ](https://bintray.com/prasannajeet89/praszappsMaven/janus/_latestVersion) [![Apache 2.0](https://img.shields.io/hexpm/l/plug.svg)](https://gitlab.com/prasannajeet/Janus/blob/master/LICENSE)
### Currently in alpha - may be buggy

**Tired of having to write an entire chunk of code, check for OS versions, create layouts and store icons to implement a simple biometric authentication dialog?**

This Android library project is designed to simplify the complicated biometric authentication process in Android.

**Note: The library is configured to AndroidX and hence will work only on AndroidX applications. Ensure your application is configured to use AndroidX using the "Refactor->Migrate to AndroidX" option in Android Studio 3.2 and above.**

Upon completion, it will offer following biometric authentication

- Biometric Authentication Dialog - Fingerprint slider for pre-Android P and biometric slider for Android P and above

This is a one-step process - Call authentication UI with type of authentication and context, then listen for results via listener or by observing a `MutableLiveData<JanusAuthenticationResponse>` returned

## Gradle Dependency

In the module level build.gradle, add the following dependency
```
implementation 'com.praszapps.biometric:janus:{latest_release_see_badge_on_top}'
```

## Sample code - Using listener
**Kotlin**
```
JanusAuthenticator.authenticate(JanusAuthenticationStyle.BIOMETRIC_DIALOG, this, object : JanusAuthenticationCallback {
    override fun onAuthenticationResponse(authenticationResponse: JanusAuthenticationResponse) {
        when(authenticationResponse) {
            is JanusAuthenticationResponse.Success -> {
                Toast.makeText(this@MainActivity, "Successful Auth", Toast.LENGTH_LONG).show()
            }
            is JanusAuthenticationResponse.DeviceApiLevelBelow23 -> {
                Toast.makeText(this@MainActivity, "FailureReason: API level is below 23 for device", Toast.LENGTH_LONG).show()
            }
            is JanusAuthenticationResponse.ErrorDuringFingerprintAuthentication -> {
                Toast.makeText(this@MainActivity, "Auth failed with reason - ${authenticationResponse.errorMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
})
```
**Java**
```
JanusAuthenticator.INSTANCE.authenticate(JanusAuthenticationStyle.BIOMETRIC_DIALOG, this, new JanusAuthenticationCallback() {
    @Override
    public void onAuthenticationResponse(@NotNull JanusAuthenticationResponse janusAuthenticationResponse) {
        if(janusAuthenticationResponse instanceof JanusAuthenticationResponse.Success){
            Toast.makeText(MainActivity.this, "Authentication Success!", Toast.LENGTH_SHORT).show();
        } else if(janusAuthenticationResponse instanceof JanusAuthenticationResponse.DeviceApiLevelBelow23) {
            Toast.makeText(MainActivity.this, "Device error below API 23", Toast.LENGTH_SHORT).show();
        } else if(janusAuthenticationResponse instanceof JanusAuthenticationResponse.ErrorDuringFingerprintAuthentication) {
            Toast.makeText(MainActivity.this, ((JanusAuthenticationResponse.ErrorDuringFingerprintAuthentication) janusAuthenticationResponse).getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }
});
```

## Sample code - Using LiveData
**Kotlin**
```
JanusAuthenticator.authenticate(JanusAuthenticationStyle.BIOMETRIC_DIALOG, this@MainActivity).observe(this@MainActivity, Observer {response ->
    when(response) {
        is JanusAuthenticationResponse.Success -> {
            Toast.makeText(this@MainActivity, "Auth Success", Toast.LENGTH_SHORT).show()
        }

        is JanusAuthenticationResponse.DeviceApiLevelBelow23 -> {
            Toast.makeText(this@MainActivity, "Device below API 23", Toast.LENGTH_SHORT).show()
        }

        is JanusAuthenticationResponse.ErrorDuringFingerprintAuthentication -> {
            Toast.makeText(this@MainActivity, "Error - ${response.errorMessage}", Toast.LENGTH_SHORT).show()
        }
    }
})
```
**Java**
```
JanusAuthenticator.INSTANCE.authenticate(JanusAuthenticationStyle.BIOMETRIC_DIALOG, MainActivity.this).observe(MainActivity.this, new Observer<JanusAuthenticationResponse>() {
    @Override
    public void onChanged(JanusAuthenticationResponse janusAuthenticationResponse) {
        if(janusAuthenticationResponse instanceof JanusAuthenticationResponse.Success){
            Toast.makeText(MainActivity.this, "Authentication Success!", Toast.LENGTH_SHORT).show();
        } else if(janusAuthenticationResponse instanceof JanusAuthenticationResponse.DeviceApiLevelBelow23) {
            Toast.makeText(MainActivity.this, "Device error below API 23", Toast.LENGTH_SHORT).show();
        } else if(janusAuthenticationResponse instanceof JanusAuthenticationResponse.ErrorDuringFingerprintAuthentication) {
            Toast.makeText(MainActivity.this, ((JanusAuthenticationResponse.ErrorDuringFingerprintAuthentication) janusAuthenticationResponse).getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }
});
```

For now the UI will be the default UI provided by the library, eventually override options will be provided

## Release Notes
Release notes can be found [here](https://gitlab.com/prasannajeet/Janus/blob/master/CHANGELOG.md)

## Notable Development Milestones
- **v0.4.5** - Converted project to AndroidX *(September 24 2018)*
- **v0.3.1** - Converted fingerprint dialog to bottom sheet for pre Pie API level *(September 19 2018)*
- **v0.3.0** - Re-branded Fingertip to Janus *(September 19 2018)*
- **v0.2.2** - Stabilized fingerprint dialog. Added coroutines. Removed RxJava. Configured proguard. *(September 18 2018)*
- **v0.2.0** - Fragment based fingerprint authentication implemented with initial UI *(September 15 2018)*
- **v0.1.3** - Converted project to Kotlin *(September 15 2018)*
- **v0.1.2** - Initial Commit *(August 9 2018)*


## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgitlab.com%2Fprasannajeet%2FJanus.svg?type=large)](https://app.fossa.io/projects/git%2Bgitlab.com%2Fprasannajeet%2FJanus?ref=badge_large)

```
   Copyright [2018] [Prasannajeet Pani]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```
