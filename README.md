# Fingertip - Now in Kotlin!
[![CircleCI](https://circleci.com/gh/prasannajeet/Fingertip/tree/master.svg?style=svg)](https://circleci.com/gh/prasannajeet/Fingertip/tree/master) [![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip?ref=badge_shield)

Fingertip - The easy fingerprint authentication library for Android
### Currently in alpha (v0.1) not ready for consumption

This Android library project is designed to simplify the complicated fingerprint authentication process in Android.

It offers 3 types of Fingerprint authentication UI

- Fingerprint Authentication Dialog (both pre and post SDK 28 versions) (Implementation Complete, refactor pending)
- Device lock based Authentication (will work only if fingerprint available) (In Progress)
- Fingerprint Authentication Activity (TBD)

For now the UI will be the default UI provided by the library, eventually override options will be provided

There are 2 steps in conducting the authentication
1. Set configuration such as type of authentication (dialog/activity/device lock) 
2. Call authentication API and listen for results

### Development Milestones
- v0.2.0 - Fragment based fingerprint authentication implemented with initial UI (September 15 2018)
- v0.1.3 - Converted project to Kotlin (September 15 2018)
- v0.1.2 - Initial Commit (August 9 2018


## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fprasannajeet%2FFingertip?ref=badge_large)
