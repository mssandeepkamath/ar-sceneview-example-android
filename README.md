# Google Sceneform is now maintained by SceneView!

## Previews:
<img src="https://user-images.githubusercontent.com/90695071/224489664-e27bedd4-84d5-43b3-90c5-92119c5e09b9.png" width="230" height="500"> <img src="https://user-images.githubusercontent.com/90695071/224489671-de367d09-d585-4633-998d-1d2f1e482167.png" width="230" height="500">


## Documentation

> build.gradle (Module level)

    implementation "com.gorisse.thomas.sceneform:sceneform:1.21.0"
  
> AndroidManifest.xml

    <uses-permission android:name="android.permission.CAMERA" />
    
    <application>
       ...
       <meta-data
            android:name="com.google.ar.core"
            android:value="optional" />
    </application>

> activity_ar.xml

       <androidx.fragment.app.FragmentContainerView
            android:id="@+id/arFragment"
            android:name="com.google.ar.sceneform.ux.ArFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
            
> ArActivity.java

Check your device compatibilty with ARcore:

      public static boolean checkSystemSupport(Activity activity) {
          // checking whether the API version of the running Android >= 24
          // that means Android Nougat 7.0
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
              String openGlVersion = ((ActivityManager) Objects.requireNonNull(activity.getSystemService(Context.ACTIVITY_SERVICE))).getDeviceConfigurationInfo().getGlEsVersion();
              // checking whether the OpenGL version >= 3.0
              if (Double.parseDouble(openGlVersion) >= 3.0) {
                  return true;
              } else {
                  Toast.makeText(activity, "App needs OpenGl Version 3.0 or later", Toast.LENGTH_SHORT).show();
                  activity.finish();
                  return false;
              }
          } else {
              Toast.makeText(activity, "App does not support required Build Version", Toast.LENGTH_SHORT).show();
              activity.finish();
              return false;
          }
      }
      
Create anchor and Model renderable:

    if (checkSystemSupport(this)) {
              //Connecting to the UI
              // ArFragment is linked up with its respective id used in the activity_main.xml
              arCam = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

              assert arCam != null;
              arCam.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                  clickNo++;
                  //rendering the model when click is 1
                  if (clickNo == 1) {
                      //fixing the coordinates in the detected plane
                      Anchor anchor = hitResult.createAnchor();
                      ModelRenderable.builder()
                              .setSource(this,R.raw.tshirt) // set glb model
                              .setIsFilamentGltf(true)
                              .build()
                              .thenAccept(modelRenderable -> addModel(anchor, modelRenderable))
                              .exceptionally(throwable -> {
                                  AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                  builder.setMessage("Something is not right" + throwable.getMessage()).show();
                                  return null;
                              });
                  }
              });
          }
          
Render model:

      private void addModel(Anchor anchor, ModelRenderable modelRenderable) {
              // Creating a AnchorNode with a specific anchor
              AnchorNode anchorNode = new AnchorNode(anchor);

              // attaching the anchorNode with the ArFragment
              anchorNode.setParent(arCam.getArSceneView().getScene());

              // attaching the anchorNode with the TransformableNode
              TransformableNode model = new TransformableNode(arCam.getTransformationSystem());
              model.setParent(anchorNode);
              // attaching the 3d model with the TransformableNode
              // that is already attached with the node
              model.setRenderable(modelRenderable);
              model.select();
          }
         
 > Learn more:
 
 https://github.com/SceneView
      
