package com.sunshineapps.riftexample;


import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DECAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_SPOT_CUTOFF;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLightf;
import static org.lwjgl.opengl.GL11.glLightfv;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrixf;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexEnvf;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.ovr.OVR.ovrEye_Count;
import static org.lwjgl.ovr.OVR.ovrEye_Left;
import static org.lwjgl.ovr.OVR.ovrEye_Right;
import static org.lwjgl.ovr.OVR.ovrHmd_ConfigureTracking;
import static org.lwjgl.ovr.OVR.ovrHmd_Create;
import static org.lwjgl.ovr.OVR.ovrHmd_CreateDebug;
import static org.lwjgl.ovr.OVR.ovrHmd_DK2;
import static org.lwjgl.ovr.OVR.ovrHmd_Detect;
import static org.lwjgl.ovr.OVR.ovrHmd_GetFovTextureSize;
import static org.lwjgl.ovr.OVR.ovrHmd_GetFrameTiming;
import static org.lwjgl.ovr.OVR.ovrHmd_GetRenderDesc;
import static org.lwjgl.ovr.OVR.ovrHmd_GetTrackingState;
import static org.lwjgl.ovr.OVR.ovrHmd_RecenterPose;
import static org.lwjgl.ovr.OVR.ovrHmd_SubmitFrame;
import static org.lwjgl.ovr.OVR.ovrLayerFlag_TextureOriginAtBottomLeft;
import static org.lwjgl.ovr.OVR.ovrLayerType_EyeFov;
import static org.lwjgl.ovr.OVR.ovrRenderAPI_OpenGL;
import static org.lwjgl.ovr.OVR.ovrSuccess;
import static org.lwjgl.ovr.OVR.ovrSuccess_NotVisible;
import static org.lwjgl.ovr.OVR.ovrTrackingCap_MagYawCorrection;
import static org.lwjgl.ovr.OVR.ovrTrackingCap_Orientation;
import static org.lwjgl.ovr.OVR.ovrTrackingCap_Position;
import static org.lwjgl.ovr.OVR.ovr_GetVersionString;
import static org.lwjgl.ovr.OVR.ovr_Initialize;
import static org.lwjgl.ovr.OVR.ovr_Shutdown;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memDecodeASCII;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.ovr.OVREyeRenderDesc;
import org.lwjgl.ovr.OVRFovPort;
import org.lwjgl.ovr.OVRFrameTiming;
import org.lwjgl.ovr.OVRHmdDesc;
import org.lwjgl.ovr.OVRInitParams;
import org.lwjgl.ovr.OVRLayerEyeFov;
import org.lwjgl.ovr.OVRLogCallback;
import org.lwjgl.ovr.OVRMatrix4f;
import org.lwjgl.ovr.OVRPosef;
import org.lwjgl.ovr.OVRSizei;
import org.lwjgl.ovr.OVRTexture;
import org.lwjgl.ovr.OVRTextureHeader;
import org.lwjgl.ovr.OVRTrackingState;
import org.lwjgl.ovr.OVRUtil;
import org.lwjgl.ovr.OVRVector3f;
import org.lwjgl.system.MemoryUtil;
import org.saintandreas.math.Matrix4f;
import org.saintandreas.math.Vector3f;

import com.sunshineapps.riftexample.thirdparty.FixedTexture;
import com.sunshineapps.riftexample.thirdparty.FixedTexture.BuiltinTexture;
import com.sunshineapps.riftexample.thirdparty.FrameBuffer;
import com.sunshineapps.riftexample.thirdparty.MatrixStack;

public final class RiftClient0600 {
    
    //OVR
    private final float eyeHeight = 1.8f;       // OvrLibrary.OVR_DEFAULT_EYE_HEIGHT;  <- cant find this
    private final float ipd = 0.06f; //no idea!    OvrLibrary.OVR_DEFAULT_IPD;
    private final boolean useDebugHMD = true;
    private final int riftWidth = 1920; // DK2
    private final int riftHeight = 1080; // DK2

    private long hmd;
    private OVRHmdDesc hmdDesc;
    private int resolutionW;        //TODO mirror screen
    private int resolutionH;        //TODO
    private final int[] eyeRenderOrder = new int[2];   //performance tip from developer guide for screens like DK2
    private final OVRTexture textures[] = new OVRTexture[2];
    private final OVRMatrix4f[] projections = new OVRMatrix4f[2];
    private final OVRFovPort fovPorts[] = new OVRFovPort[2];
    private final OVRPosef eyePoses[] = new OVRPosef[2];
    private OVREyeRenderDesc eyeRenderDesc[] = new OVREyeRenderDesc[2];
    private OVRSizei textureSize;
    private OVRLayerEyeFov layer0;
   
    //OpenGL
    private final FloatBuffer projectionDFB[];
    private final FloatBuffer modelviewDFB;
    private FrameBuffer eyeDFB[] = new FrameBuffer[2];
    private FixedTexture cheq;
    
    //GLFW
    private static long window;
    private GLFWErrorCallback errorfun;
    private GLFWKeyCallback keyfun;  
    
    // Scene
    private Matrix4f player;
    
    //FPS
    private final int fpsReportingPeriodSeconds = 5;
    private final ScheduledExecutorService fpsCounter = Executors.newSingleThreadScheduledExecutor();
    private final AtomicInteger frames = new AtomicInteger(0);
    private final AtomicInteger fps = new AtomicInteger(0);
    Runnable fpsJob = new Runnable() {
        public void run() {
            int frameCount = frames.getAndSet(0);
            fps.set(frameCount/fpsReportingPeriodSeconds);
            frames.addAndGet(frameCount-(fps.get()*fpsReportingPeriodSeconds));
            System.out.println(frameCount+" frames in "+fpsReportingPeriodSeconds+"s. "+fps.get()+"fps");
        }
    };
    
    public RiftClient0600() {
        System.out.println("RiftClient0600()");
        modelviewDFB =  ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        projectionDFB = new FloatBuffer[2];
        for (int eye = 0; eye < 2; ++eye) {
            projectionDFB[eye] = ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        }
    }
    
    public final void drawPlaneXZ() {
        glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_DECAL);
        float roomSize = 4.0f;
        float tileSize = 4.0f; // if same then there are two tiles per square
        glBegin(GL_QUADS);
        {
            glNormal3f(0f, 1f, 0f);
            glColor4f(1f, 1f, 1f, 1f);
            glTexCoord2f(0f, 0f);
            glVertex3f(-roomSize, 0f, -roomSize);
            glTexCoord2f(tileSize, 0f);
            glVertex3f(roomSize, 0f, -roomSize);
            glTexCoord2f(tileSize, tileSize);
            glVertex3f(roomSize, 0f, roomSize);
            glTexCoord2f(0f, tileSize);
            glVertex3f(-roomSize, 0f, roomSize);
        }
        glEnd();
    }
    
    private void recenterView() {
        Vector3f center = Vector3f.UNIT_Y.mult(eyeHeight);
        Vector3f eye = new Vector3f(ipd * 5.0f, eyeHeight, 0.0f);
        player = Matrix4f.lookat(eye, center, Vector3f.UNIT_Y).invert();
        ovrHmd_RecenterPose(hmd);
    }

    private void run() {
        System.out.println(""+System.getProperty("java.version"));
        
        // step 1 - hmd init
        System.out.println("step 1 - hmd init");
        OVRLogCallback callback = new OVRLogCallback() {
            @Override
            public void invoke(int level, long message) {
                System.out.println("LibOVR [" + level + "] " + memDecodeASCII(message));
            }
        };
        OVRInitParams initParams = new OVRInitParams();
        initParams.setLogCallback(callback.getPointer());
       // initParams.setFlags(ovrInit_Debug);
        if  (ovr_Initialize(initParams.buffer()) != ovrSuccess) {
            System.out.println("init failed");
        }
        System.out.println("ovr_GetVersionString = " + ovr_GetVersionString());
        System.out.println("ovrHmd_Detect = " + ovrHmd_Detect());
        
        // step 2 - hmd create
        System.out.println("step 2 - hmd create");
        PointerBuffer pHmd = BufferUtils.createPointerBuffer(1);
        if (ovrHmd_Create(0, pHmd) != ovrSuccess) {
            if (!useDebugHMD) {
                System.out.println("debug disabled");
                return;
            }
            System.out.println("create failed, try debug");
            if (ovrHmd_CreateDebug(ovrHmd_DK2, pHmd) != 0) {
                System.out.println("debug failed, quit");
                return;
            }
        }
        hmd = pHmd.get(0);

        // step 3 - hmd size queries
        hmdDesc = new OVRHmdDesc(MemoryUtil.memByteBuffer(hmd, OVRHmdDesc.SIZEOF));
        System.out.println("step 3 - hmd sizes");
//        System.out.println("Serial Number: "+hmdDesc.getSerialNumberString());
        resolutionW = hmdDesc.getResolutionW();
        resolutionH = hmdDesc.getResolutionH();
        System.out.println("resolution W=" + resolutionW + ", H=" + resolutionH);
        
        OVRSizei pairInts = new OVRSizei();             //bit naughty using this in case or of w and h changes
        hmdDesc.getEyeRenderOrder(pairInts.buffer());
        eyeRenderOrder[0] = pairInts.getW();
        eyeRenderOrder[1] = pairInts.getH();
        
        for (int eye = 0; eye < 2; eye++) {
            fovPorts[eye] = new OVRFovPort();
            hmdDesc.getDefaultEyeFov(fovPorts[eye].buffer(), eye);
            System.out.println("eye "+eye+" = "+fovPorts[eye].getUpTan() +", "+ fovPorts[eye].getDownTan()+", "+fovPorts[eye].getLeftTan()+", "+fovPorts[eye].getRightTan());
        }
        
        float pixelsPerDisplayPixel = 1.0f;
        OVRSizei leftTextureSize = new OVRSizei();
        ovrHmd_GetFovTextureSize(hmd, ovrEye_Left, fovPorts[ovrEye_Left].buffer(), pixelsPerDisplayPixel, leftTextureSize.buffer());
        System.out.println("leftTextureSize W="+leftTextureSize.getW() +", H="+ leftTextureSize.getH());
        
        OVRSizei rightTextureSize = new OVRSizei();
        ovrHmd_GetFovTextureSize(hmd, ovrEye_Right, fovPorts[ovrEye_Right].buffer(), pixelsPerDisplayPixel, rightTextureSize.buffer());
        System.out.println("rightTextureSize W="+rightTextureSize.getW() +", H="+ rightTextureSize.getH());
        
        
        int displayW = leftTextureSize.getW() + rightTextureSize.getW();
        int displayH = Math.max(leftTextureSize.getH(), rightTextureSize.getH());
        textureSize = new OVRSizei(OVRSizei.malloc(displayW / 2, displayH));    //single eye
        System.out.println("renderTargetEyeSize W=" + textureSize.getW() + ", H=" + textureSize.getH());

//        eyeRenderViewport[ovrEye_Left].Pos = new OvrVector2i(0, 0);
//        eyeRenderViewport[ovrEye_Left].Size = renderTargetEyeSize;
//        eyeRenderViewport[ovrEye_Right].Pos = eyeRenderViewport[ovrEye_Left].Pos;
//        eyeRenderViewport[ovrEye_Right].Size = renderTargetEyeSize;

        
        OVRTextureHeader leftHeader = new OVRTextureHeader();
        leftHeader.setAPI(ovrRenderAPI_OpenGL);
        leftHeader.setTextureSize(textureSize.buffer());
        //leftHeader.setTextureSizeW(renderTargetEyeSize.getW());
        //leftHeader.setTextureSizeH(renderTargetEyeSize.getH());
        textures[ovrEye_Left] = new OVRTexture();
        textures[ovrEye_Left].setHeader(leftHeader.buffer());
        //leftTexture.setPlatformData(PlatformData);
        
        OVRTextureHeader rightHeader = new OVRTextureHeader();
        rightHeader.setAPI(ovrRenderAPI_OpenGL);
        rightHeader.setTextureSize(textureSize.buffer());
        textures[ovrEye_Right] = new OVRTexture();
        textures[ovrEye_Right].setHeader(rightHeader.buffer()); 
        
//from snippet        ovrHmd_SetEnabledCaps(hmd, ovrHmdCap_LowPersistence|ovrHmdCap_DynamicPrediction);
        
        // step 4 - tracking
        System.out.println("step 4 - tracking");
        if (ovrHmd_ConfigureTracking(hmd, ovrTrackingCap_Orientation|ovrTrackingCap_MagYawCorrection|ovrTrackingCap_Position, 0) != ovrSuccess) {
            throw new IllegalStateException("Unable to start the sensor");
        }
        
        // step 5 - Projections
        System.out.println("step 5 - Projections");
        for (int eye = 0; eye < 2; eye++) {
            projections[eye] = new OVRMatrix4f();
            OVRUtil.ovrMatrix4f_Projection(fovPorts[eye].buffer(), 0.1f, 1000000f, OVRUtil.ovrProjection_RightHanded, projections[eye].buffer());
        }
        
        // step 6 - player params
        System.out.println("step 6 - player params");
        for (int eye = 0; eye < 2; eye++) {
            eyeRenderDesc[eye] = new OVREyeRenderDesc();
            ovrHmd_GetRenderDesc(hmd, ovrEye_Left,  fovPorts[eye].buffer(), eyeRenderDesc[eye].buffer());
        }
        
        
//        ipd = hmd.getFloat(OvrLibrary.OVR_KEY_IPD, ipd);
//        eyeHeight = hmd.getFloat(OvrLibrary.OVR_KEY_EYE_HEIGHT, eyeHeight);
        recenterView();
//        System.out.println("eyeheight=" + eyeHeight + " ipd=" + ipd);

        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
        try {
            init();
            loop();
            glfwDestroyWindow(window);
        } finally {
            glfwTerminate();
            fpsCounter.shutdown();
        } 
  //       ovrHmd_Destroy(hmd.getPointer());
         ovr_Shutdown();
    }
    
    
    private void init() {
        // step 7 - opengl window
        System.out.println("step 7 - window");
        
        errorfun = errorCallbackPrint(System.err);
        glfwSetErrorCallback(errorfun);
        
        if (glfwInit() != GL_TRUE) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        window = glfwCreateWindow(riftWidth, riftHeight, "Hello World!", NULL, NULL); // where is this text used?
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        keyfun = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( action != GLFW_RELEASE) {
                    return;
                }
                switch (key) {
                    case GLFW_KEY_ESCAPE:
                        glfwSetWindowShouldClose(window, GL_TRUE);
                        break;
                    case GLFW_KEY_R:
                        recenterView();
                        break;
                }
            }
        };
        glfwSetKeyCallback(window, keyfun);
        

        glfwMakeContextCurrent(window);
     //   glfwSwapInterval(0);              //not needed?
        glfwShowWindow(window);
        
//        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);        //only after display create?
//        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);        //only after display create?`
        //glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);        //hide mouse
        
        GLContext.createFromCurrent();
        glClearColor(.42f, .67f, .87f, 1f);
        
        // Lighting
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_COLOR_MATERIAL);

        FloatBuffer lightPos = ByteBuffer.allocateDirect(4*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        lightPos.put(new float[]{0.5f, 0.0f, 1.0f, 0.0001f});
        lightPos.rewind();
        
        FloatBuffer noAmbient = ByteBuffer.allocateDirect(4*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        noAmbient.put(new float[]{0.2f, 0.2f, 0.2f, 1.0f});
        noAmbient.rewind();
        
        FloatBuffer diffuse = ByteBuffer.allocateDirect(4*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        diffuse.put(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        diffuse.rewind();
        
        FloatBuffer spec = ByteBuffer.allocateDirect(4*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        spec.put(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        spec.rewind();
        
        glLightfv(GL_LIGHT0, GL_AMBIENT, noAmbient);
        glLightfv(GL_LIGHT0, GL_SPECULAR, spec);
        glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuse);
        glLightfv(GL_LIGHT0, GL_POSITION, lightPos);
        glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 45.0f);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

//        for (int eye = 0; eye < 2; ++eye) {
//            eyeOffsets[eye].x = eyeRenderDescs[eye].HmdToEyeViewOffset.x;
//            eyeOffsets[eye].y = eyeRenderDescs[eye].HmdToEyeViewOffset.y;
//            eyeOffsets[eye].z = eyeRenderDescs[eye].HmdToEyeViewOffset.z;
//        }

        //Create FB Textures
        eyeDFB[ovrEye_Left] = new FrameBuffer(textureSize.getW(), textureSize.getH());
  //      leftTexture.setPlatformData(PlatformData);
        eyeDFB[ovrEye_Right] = new FrameBuffer(textureSize.getW(), textureSize.getH());

  //      eyeTextures[ovrEye_Left].ogl.TexId = eyeDFB[ovrEye_Left].getTexture().id;
  //      eyeTextures[ovrEye_Right].ogl.TexId = eyeDFB[ovrEye_Right].getTexture().id;

        // scene prep
        glEnable(GL_TEXTURE_2D);
        cheq = FixedTexture.createBuiltinTexture(BuiltinTexture.tex_checker);
        glDisable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);
        
        // initial matrix stuff
        glMatrixMode(GL_PROJECTION);
        for (int eye = 0; eye < ovrEye_Count; ++eye) {
            glLoadMatrixf(projections[eye].buffer());           //is the ordering the same?
        }

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        recenterView();
        MatrixStack.MODELVIEW.set(player.invert());

        modelviewDFB.clear();
        MatrixStack.MODELVIEW.top().fillFloatBuffer(modelviewDFB, true);
        modelviewDFB.rewind();
        glLoadMatrixf(modelviewDFB);
        
        //Layers
        layer0 = new OVRLayerEyeFov();
        layer0.setHeaderType(ovrLayerType_EyeFov);
        layer0.setHeaderFlags(ovrLayerFlag_TextureOriginAtBottomLeft);
        for (int eye = 0; eye < 2; eye++) {
            layer0.setColorTexture(textures[eye].buffer(), eye);
            layer0.setViewport(textureSize.buffer(), eye);
            layer0.setFov(fovPorts[eye].buffer(), eye);
            //not we update pose only when we have it in the render loop
        }

        //fps
        fpsCounter.scheduleAtFixedRate(fpsJob, 0, fpsReportingPeriodSeconds, TimeUnit.SECONDS); 
    }
    
    private void loop() {
        // step 8 - animator loop
        System.out.println("step 8 - animator loop");
        while (glfwWindowShouldClose(window) == GL_FALSE) {
            
            OVRFrameTiming ftiming = new OVRFrameTiming(); 
            ovrHmd_GetFrameTiming(hmd, 0, ftiming.buffer());
            OVRTrackingState hmdState = new OVRTrackingState();
            ovrHmd_GetTrackingState(hmd, ftiming.getDisplayMidpointSeconds(), hmdState.buffer());

            //get head pose
            OVRPosef headPose = new OVRPosef();
            hmdState.getHeadPoseThePose(headPose.buffer());

            //build view offsets struct
            ByteBuffer hmdToEyeViewOffset = BufferUtils.createByteBuffer(2 * OVRVector3f.SIZEOF);
            eyeRenderDesc[ovrEye_Left].getHmdToEyeViewOffset(hmdToEyeViewOffset);
            hmdToEyeViewOffset.position(OVRVector3f.SIZEOF);
            eyeRenderDesc[ovrEye_Right].getHmdToEyeViewOffset(hmdToEyeViewOffset);
            hmdToEyeViewOffset.position(0);

            //calc eye poses
            ByteBuffer outEyePoses = BufferUtils.createByteBuffer(OVRPosef.SIZEOF * 2);
            OVRUtil.ovr_CalcEyePoses(headPose.buffer(), hmdToEyeViewOffset, outEyePoses);
            
           //Retrieve the eye poses
            outEyePoses.limit(OVRPosef.SIZEOF);
            eyePoses[ovrEye_Left] = new OVRPosef(outEyePoses.slice().order(outEyePoses.order()));
            outEyePoses.position(OVRPosef.SIZEOF);
            outEyePoses.limit(2 * OVRPosef.SIZEOF);
            eyePoses[ovrEye_Right] = new OVRPosef(outEyePoses.slice().order(outEyePoses.order()));
            

            
            
            
            
            //dont need this yet! unless using      ovrLayerType_QuadInWorld or ovrLayerType_QuadHeadLocked
//          OVRViewScaleDesc viewScaleDesc = new OVRViewScaleDesc();
//          viewScaleDesc.setHmdSpaceToWorldScaleInMeters(1.0f);
//          viewScaleDesc.HmdToEyeViewOffset[0] = ViewOffset[0];
//          viewScaleDesc.HmdToEyeViewOffset[1] = ViewOffset[1];
//          int result = ovrHmd_SubmitFrame(hmd, 0, viewScaleDesc.buffer(), layers.buffer(), 1);



            
            
          //  Posef eyePoses[] = hmd.getEyePoses(frameCount, eyeOffsets);
            for (int eyeIndex = 0; eyeIndex < ovrEye_Count; eyeIndex++) {
                int eye = eyeRenderOrder[eyeIndex];
                OVRPosef eyePose = eyePoses[eye];
                layer0.setRenderPose(eyePose.buffer(), eye);

                eyeDFB[eye].activate();
                
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

                glMatrixMode(GL_PROJECTION);
                glLoadMatrixf(projectionDFB[eye]);

                glMatrixMode(GL_MODELVIEW);
                MatrixStack mv = MatrixStack.MODELVIEW;
                mv.push();
                {
       //             mv.preTranslate(RiftUtils.toVector3f(eyePoses[eye].Position).mult(-1));
       //             mv.preRotate(RiftUtils.toQuaternion(eyePoses[eye].Orientation).inverse());
                    mv.translate(new Vector3f(0, eyeHeight, 0));
                    modelviewDFB.clear();
                    MatrixStack.MODELVIEW.top().fillFloatBuffer(modelviewDFB, true);
                    modelviewDFB.rewind();
                    glLoadMatrixf(modelviewDFB);

                    // tiles on floor
                    glEnable(GL_TEXTURE_2D);
                    glBindTexture(GL_TEXTURE_2D, cheq.getId());
                    glTranslatef(0.0f, -eyeHeight, 0.0f);
                    drawPlaneXZ();
                    glTranslatef(0.0f, eyeHeight, 0.0f);
                    glDisable(GL_TEXTURE_2D);
                }
                mv.pop();
            }
            ARBFramebufferObject.glBindFramebuffer(ARBFramebufferObject.GL_FRAMEBUFFER, 0);
            glBindTexture(GL_TEXTURE_2D, 0);
            glDisable(GL_TEXTURE_2D);
            glfwPollEvents();

            frames.incrementAndGet();
            
            
            int result = ovrHmd_SubmitFrame(hmd, 0, null, layer0.buffer(), 1);
            if (result == ovrSuccess_NotVisible) {
                System.out.println("TODO not vis!!");
            } else if (result != ovrSuccess) {
                System.out.println("TODO failed submit");
            }
        }
    }

    public static void main(String[] args) {
        new RiftClient0600().run();
    }
}