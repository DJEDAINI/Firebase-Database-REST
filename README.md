Firebase-Database-REST
=======

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MaterialViewPager-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1731)
[![Android Weekly](https://img.shields.io/badge/android--weekly-151-blue.svg)](http://androidweekly.net/issues/issue-151)
[![CircleCI](https://circleci.com/gh/florent37/MaterialViewPager.svg?style=svg)](https://circleci.com/gh/florent37/MaterialViewPager)

Material Design ViewPager used in this project

[![Build screen](https://raw.githubusercontent.com/florent37/MaterialViewPager/master/screenshots/screenshot_2_small.png)](http://youtu.be/g6tTDVceM9E)

#Sample

<a href="https://play.google.com/store/apps/details?id=com.github.florent37.materialviewpager.sample">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

You can find a sample APK : [Link](https://github.com/florent37/MaterialViewPager/releases/download/1.1.0/sample-release.apk)

And have a look on a sample Youtube Video : [Youtube Link](http://www.youtube.com/watch?v=r95Tt6AS18c)

#Download

In your module [![Download](https://api.bintray.com/packages/florent37/maven/MaterialViewPager/images/download.svg)](https://bintray.com/florent37/maven/MaterialViewPager/_latestVersion)
```groovy
compile 'com.github.florent37:materialviewpager:1.2.1'

//dependencies
compile 'com.flaviofaria:kenburnsview:1.0.7'
compile 'com.jpardogo.materialtabstrip:library:1.1.0'
compile 'com.github.bumptech.glide:glide:3.7.0'
```

#Usage

Add MaterialViewPager to your activity's layout
```xml
<com.github.florent37.materialviewpager.MaterialViewPager
    android:id="@+id/materialViewPager"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:viewpager_logo="@layout/header_logo"
    app:viewpager_logoMarginTop="100dp"
    app:viewpager_color="@color/colorPrimary"
    app:viewpager_headerHeight="200dp"
    app:viewpager_headerAlpha="1.0"
    app:viewpager_hideLogoWithFade="false"
    app:viewpager_hideToolbarAndTitle="true"
    app:viewpager_enableToolbarElevation="true"
    app:viewpager_parallaxHeaderFactor="1.5"
    app:viewpager_headerAdditionalHeight="20dp"
    app:viewpager_displayToolbarWhenSwipe="true"
    app:viewpager_transparentToolbar="true"
    app:viewpager_animatedHeaderImage="true"
    app:viewpager_disableToolbar="false"

    />
```
![alt preview](https://raw.github.com/florent37/MaterialViewPager/master/screenshots/preview_small.png)
