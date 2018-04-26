# cite 
https://github.com/nbsp-team/MaterialFilePicker

# usage 



# structure 
filter/HiddenFilter
filter/PatternFilter 
filter/CompositeFilter
utils/FileComparator 
utils/FileUtils
widget/EmptyRecyclerView
utils/FileTypeUtils <- drawable/icons 
utils/FileTypeUtils <- values/string
ui/DirectoryFragment <- FileTypeUtils
ui/DirectoryFragment <- DirectoryAdapter
ui/DirectoryFragment <- R.layout.fragment_directory
ui/DirectoryFragment <- R.id.directory_recycler_view
ui/DirectoryFragment <- R.id.directory_empty_view

# main chain 
MainActivity <- MaterialFilePicker <- other packages 
MainActivity <- SampleFragment <- {FilePickerActivity|R.layout.fragment_sample|}

# working


# package/ui
DirectoryAdapter -> DirectoryFragment -> FilePickerActivity

# res
item_file.xml -> DirectoryAdapter
menu.xml -> FilePickerActivity
activity_file_picker.xml -> FilePickerActivity
fragment_directory.xml -> DirectoryFragment
fragment_directory.xml <- EmptyRecyclerView
activity_main -> MainActivity 

# requirements 
- app or library dependency 
```xml
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support:recyclerview-v7:25.3.1'
    compile 'com.android.support:design:25.3.1'
```

# requirements/ declare activity 
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.nbsp.materialfilepicker">
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <application>

        <activity android:name=".ui.FilePickerActivity"
            android:theme="@style/MFP_BaseTheme">
        </activity>
    </application>
</manifest>

```

# icons 
https://www.iconfinder.com/iconsets/hawcons



# Structure 
MaterialFilePicker <- FilePickerActivity <- DirectoryFragment <- CompositeFilter



# Material File Picker [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MaterialFilePicker-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2690) 
Material file picker library for Android

![](https://i.imgur.com/mjxs05n.png)

## how to use 

Add repository url and dependency in application module gradle file:

```gradle
repositories {
    maven {
        url  "http://dl.bintray.com/lukaville/maven" 
    }
}

dependencies {
    compile 'com.nbsp:library:1.8'
}
```

Open file picker:

```java
new MaterialFilePicker()
    .withActivity(this)
    .withRequestCode(1)
    .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
    .withFilterDirectories(true) // Set directories filterable (false by default)
    .withHiddenFiles(true) // Show hidden files and folders
    .start();
```
or
```java
Intent intent = new Intent(this, FilePickerActivity.class);
intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.txt$"));
intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, true);
intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
startActivityForResult(intent, 1);
```

Override on activity result:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 1 && resultCode == RESULT_OK) {
        String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
        // Do anything with file
    }
}
```

Runtime permissions:

You should handle runtime permissions in activity, from what you called Material File Picker.
Look [here](https://github.com/nbsp-team/MaterialFilePicker/blob/master/app/src/main/java/com/dimorinny/sample/MainActivity.java#L38-L69) for example code.