package com.zhuoyou.plugin.running.activity;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.FishGameScore;
import com.zhuoyou.plugin.running.baas.FishRank.RankInfo;
import com.zhuoyou.plugin.running.baas.FishRank.Response;
import com.zhuoyou.plugin.running.baas.Rank;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.tools.Tools;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.xmlpull.v1.XmlPullParserException;
import twitter4j.HttpResponseCode;

public class FishGameActivity extends BaseGameActivity implements IOnSceneTouchListener, SensorEventListener {
    private static final float ANGLE = 30.0f;
    private static final int ARROW_HEIGHT = 150;
    private static final int ARROW_NORMAL = 0;
    private static final float ARROW_PADDING_Y = 65.0f;
    private static final long ARROW_RENEW_DURATION = 600;
    private static final float ARROW_SPEED = 2000.0f;
    private static final int ARROW_TYPE_HEAVY = 1;
    private static final int ARROW_TYPE_THROUGH = 2;
    private static final int ARROW_WIDTH = 42;
    private static final int BAR_HEIGHT = 90;
    private static final int BAR_SCORE_HEIGHT = 144;
    private static final int BAR_SCORE_WIDTH = 144;
    private static final int BAR_WIDTH = 270;
    private static final String BASE_PATH = "gamePic/";
    private static final String BASE_SOUND_PATH = "gameSound/";
    private static final int BOUND_BOTTOM = 750;
    private static final int BOUND_TOP = 250;
    private static final int CAMERA_HEIGHT = 1920;
    private static final int CAMERA_WIDTH = 1080;
    private static final int COIN_POWER_NUM = 500;
    private static final int FISH_NUM = 6;
    private static final int GUN_HEIGHT = 198;
    private static final float GUN_PADDING_Y = 45.0f;
    private static final int GUN_WIDTH = 180;
    private static final int JUGGLE_VALUE = 15;
    private static final int LEVEL_TEXT_SIZE = 80;
    private static final String PATH_ARROW = "gamePic/arrow.png";
    private static final String PATH_ARROW_BOOM = "gamePic/arrow_boom.png";
    private static final String PATH_ARROW_HEAVY = "gamePic/arrow_heavy.png";
    private static final String PATH_ARROW_THROUGH = "gamePic/arrow_through.png";
    private static final String PATH_BACKGROUND = "gamePic/background.png";
    private static final String PATH_BAR = "gamePic/bar.png";
    private static final String PATH_BAR_SCORE = "gamePic/ic_lvl.png";
    private static final String PATH_BAR_TIMER = "gamePic/ic_timer.png";
    private static final String PATH_BOTTOM = "gamePic/bottom_bar.png";
    private static final String PATH_BOX = "gamePic/box.png";
    private static final String PATH_BOX_BAR = "gamePic/box_bar.png";
    private static final String PATH_BOX_COIN = "gamePic/coin.png";
    private static final String PATH_BOX_COIN_1 = "gamePic/coin_1.png";
    private static final String PATH_BOX_COIN_2 = "gamePic/coin_2.png";
    private static final String PATH_END_BAR = "gamePic/end_bar.png";
    private static final String PATH_FIRE_WAVE = "gamePic/fire_wave.png";
    private static final String PATH_FONT = "gameFont/HEMIHEAD.TTF";
    private static final String PATH_GUN = "gamePic/gun.png";
    private static final String PATH_IC_AGAIN = "gamePic/ic_again.png";
    private static final String PATH_IC_END = "gamePic/ic_end.png";
    private static final String PATH_IC_SHARE = "gamePic/ic_share.png";
    private static final String PATH_IC_START_GAME = "gamePic/ic_start_game.png";
    private static final String PATH_POWER_HEAVY = "gamePic/power_heavy.png";
    private static final String PATH_POWER_SPEED = "gamePic/power_speed.png";
    private static final String PATH_POWER_THROUGH = "gamePic/power_through.png";
    private static final String PATH_SOUND_ARROW_HEAVY = "gameSound/arrow_heavy.ogg";
    private static final String PATH_SOUND_ARROW_NORMAL = "gameSound/arrow_normal.ogg";
    private static final String PATH_SOUND_ARROW_THROUGH = "gameSound/arrow_through.ogg";
    private static final String PATH_SOUND_COIN = "gameSound/coin.wav";
    private static final String PATH_SOUND_FIRE_WAVE = "gameSound/fire_wave.ogg";
    private static final String PATH_SOUND_FISH_SHOT = "gameSound/fish_shot.ogg";
    private static final String PATH_START_BAR = "gamePic/rank_bar.png";
    private static final int POWER_TYPE_BOX = 3;
    private static final int POWER_TYPE_HEAVY = 1;
    private static final int POWER_TYPE_SPEED = 0;
    private static final int POWER_TYPE_THROUGH = 2;
    private static final float ROTATION_DURATION = 3.0f;
    private static final int SCORE_ADD_TEXT_SIZE = 50;
    private static final float SCORE_BAR_PADDING_X = 100.0f;
    private static final float SCORE_BAR_PADDING_Y = 50.0f;
    private static final int SCORE_TEXT_SIZE = 50;
    private static final int START_SECOND = 60;
    private static final String TAG = "chenxin";
    private static final String TAG_FILENAME = "fileName";
    private static final String TAG_FISH = "fish";
    private static final String TAG_HEIGHT = "height";
    private static final String TAG_HIT = "hit";
    private static final String TAG_SCORE = "scroe";
    private static final String TAG_SEPPD = "speed";
    private static final String TAG_WIDTH = "width";
    private static final float TIMER_BAR_PADDING_X = 470.0f;
    private SequenceEntityModifier arrowEntiyModifier = new SequenceEntityModifier(this.arrowLoopModifier);
    private Sound arrowHeavySound;
    private ITextureRegion arrowHeavyTextureRegion;
    private LoopEntityModifier arrowLoopModifier = new LoopEntityModifier(new SequenceEntityModifier(new RotationAtModifier(ROTATION_DURATION, -30.0f, ANGLE, 21.0f, 161.0f), new RotationAtModifier(ROTATION_DURATION, ANGLE, -30.0f, 21.0f, 161.0f)));
    private Sound arrowNormalSound;
    private long arrowRenewDuration = ARROW_RENEW_DURATION;
    private ArrowSprite arrowSpriteTmp;
    private ITextureRegion arrowTextureRegion;
    private ArrayList<ITextureRegion> arrowTextureRegionList = new ArrayList();
    private Sound arrowThroughSound;
    private ITextureRegion arrowThroughTextureRegion;
    private int arrowType = 0;
    private Entity backGroundlayer = new Entity();
    private RepeatingSpriteBackground background;
    private ITextureRegion barEndTextureRegion;
    private ITextureRegion barScoreTextureRegion;
    private ITextureRegion barStartTextureRegion;
    private ITextureRegion barTextureRegion;
    private ITextureRegion barTimerTextureRegion;
    private ITextureRegion bottomTextureRegion;
    private ITextureRegion boxBarTextureRegion;
    private Rectangle boxLayer;
    private ITextureRegion boxTextureRegion;
    private boolean checkGameInfo = true;
    private ITextureRegion coin1TextureRegion;
    private ITextureRegion coin2TextureRegion;
    private Text coinNumText;
    private Sound coinSound;
    private ITextureRegion coinTextureRegion;
    private Text endLevelText;
    private Text endScoreText;
    private Sound fireWaveSound;
    private ITextureRegion fireWaveTextureRegion;
    private ArrayList<FishInfo> fishInfoList = new ArrayList();
    private Entity fishLayer = new Entity();
    private CopyOnWriteArrayList<FishSprite> fishList = new CopyOnWriteArrayList();
    private Sound fishShotSound;
    private FishGameScore gameScore;
    private LoopEntityModifier gunEntiyModifier = new LoopEntityModifier(new SequenceEntityModifier(new RotationModifier(ROTATION_DURATION, -30.0f, ANGLE), new RotationModifier(ROTATION_DURATION, ANGLE, -30.0f)));
    private Sprite gunSprite;
    private ITextureRegion gunTextureRegion;
    private Handler handler = new Handler();
    private ButtonSprite icAgain;
    private ITextureRegion icAgainTextureRegion;
    private ButtonSprite icEnd;
    private ButtonSprite icEndGame;
    private ITextureRegion icEndTextureRegion;
    private ButtonSprite icShare;
    private ITextureRegion icShareEndTextureRegion;
    private ButtonSprite icStartGame;
    private ITextureRegion icStartGameTextureRegion;
    private boolean inBoxLayer = false;
    private int level = 0;
    private Font levelFont;
    private ArrayList<LevelInfo> levelList = new ArrayList();
    private Text levelText;
    private ListView lv;
    private Rectangle pauseLayer;
    private ITextureRegion powerHeavyTextureRegion;
    private int powerShow = 0;
    private ITextureRegion powerSpeedTextureRegion;
    private ArrayList<ITextureRegion> powerTextureRegionList = new ArrayList();
    private ITextureRegion powerThroughTextureRegion;
    private CopyOnWriteArrayList<PowerUpSprite> powerUpSpriteList = new CopyOnWriteArrayList();
    private Runnable rBuildPower = new Runnable() {
        public void run() {
            Random ran = new Random();
            PowerUpSprite power = new PowerUpSprite(0.0f, (float) (ran.nextInt(1170) + 250), ran.nextInt(FishGameActivity.this.powerTextureRegionList.size() - 1));
            FishGameActivity.this.powerUpSpriteList.add(power);
            FishGameActivity.this.addEntiyOnUIThread(FishGameActivity.this.fishLayer, power);
        }
    };
    private Runnable rRemovePower = new C17439();
    private Runnable rRenewArrow = new C17417();
    private Runnable rRenewFish = new C17428();
    private Runnable rResumeGameFromBox = new Runnable() {
        public void run() {
            FishGameActivity.this.timerTask = new MyTimerTask();
            FishGameActivity.this.timer.schedule(FishGameActivity.this.timerTask, 1000, 1000);
            FishGameActivity.this.removeEntity(FishGameActivity.this.scene, FishGameActivity.this.boxLayer);
            FishGameActivity.this.inBoxLayer = false;
            if (FishGameActivity.this.stepTotal >= 500) {
                Sprite fireWave = new Sprite(0.0f, 1920.0f, FishGameActivity.this.fireWaveTextureRegion, FishGameActivity.this.getVertexBufferObjectManager()) {
                    protected void onManagedUpdate(float pSecondsElapsed) {
                        Iterator it = FishGameActivity.this.fishList.iterator();
                        while (it.hasNext()) {
                            FishSprite fishSprite = (FishSprite) it.next();
                            if (collidesWith(fishSprite)) {
                                fishSprite.mPhysicsHandler.setVelocity(0.0f, 0.0f);
                                fishSprite.mPhysicsHandler.setAcceleration(0.0f, 0.0f);
                                FishGameActivity.this.score = FishGameActivity.this.score + fishSprite.getScore();
                                if (FishGameActivity.this.score >= ((LevelInfo) FishGameActivity.this.levelList.get(FishGameActivity.this.level)).topScore) {
                                    Random ran = new Random();
                                    FishGameActivity.this.level = FishGameActivity.this.level + 1;
                                    FishGameActivity.this.levelText.setText((FishGameActivity.this.level + 1) + "");
                                    if (FishGameActivity.this.level % 2 == 0) {
                                        FishGameActivity.this.powerShow = 0;
                                        PowerUpSprite power = new PowerUpSprite(0.0f, (float) (ran.nextInt(1170) + 250), FishGameActivity.this.powerTextureRegionList.size() - 1);
                                        FishGameActivity.this.powerUpSpriteList.add(power);
                                        FishGameActivity.this.addEntiyOnUIThread(FishGameActivity.this.fishLayer, power);
                                    }
                                    FishGameActivity.this.second = FishGameActivity.this.second + 10;
                                    int count = FishGameActivity.this.fishList.size() - ((LevelInfo) FishGameActivity.this.levelList.get(FishGameActivity.this.level)).getFishCount();
                                    for (int i = 0; i < count; i++) {
                                        FishGameActivity.this.handler.post(FishGameActivity.this.rRenewFish);
                                    }
                                }
                                Log.i(FishGameActivity.TAG, "ah collisdeswish fish:" + FishGameActivity.this.score);
                                FishGameActivity.this.setTextOnUIThread(FishGameActivity.this.scoreText, FishGameActivity.this.score + "");
                                Text text = new Text(fishSprite.getX() + (fishSprite.getWidth() / 2.0f), fishSprite.getY(), FishGameActivity.this.scoreAddFont, "+" + fishSprite.getScore(), new TextOptions(HorizontalAlign.CENTER), FishGameActivity.this.getVertexBufferObjectManager());
                                final Text text2 = text;
                                text.registerEntityModifier(new MoveYModifier(1.0f, text.getY(), text.getY() - FishGameActivity.ANGLE) {
                                    protected void onModifierFinished(IEntity pItem) {
                                        FishGameActivity.this.removeEntityFromScene(text2);
                                        super.onModifierFinished(pItem);
                                    }
                                });
                                fishSprite.clearEntityModifiers();
                                final FishSprite fishSprite2 = fishSprite;
                                fishSprite.registerEntityModifier(new AlphaModifier(1.0f, FishGameActivity.SCORE_BAR_PADDING_X, 0.0f) {
                                    protected void onModifierFinished(IEntity pItem) {
                                        FishGameActivity.this.removeEntity(FishGameActivity.this.fishLayer, fishSprite2);
                                        super.onModifierFinished(pItem);
                                    }
                                });
                                text.setBlendFunction(IShape.BLENDFUNCTION_SOURCE_DEFAULT, 771);
                                FishGameActivity.this.addEntiyOnUIThread(text);
                                FishGameActivity.this.handler.postDelayed(FishGameActivity.this.rRenewFish, 1000);
                                FishGameActivity.this.fishList.remove(fishSprite);
                            }
                        }
                        if (this.mY <= (-FishGameActivity.this.fireWaveTextureRegion.getHeight())) {
                            FishGameActivity.this.removeEntity(FishGameActivity.this.scene, this);
                        }
                        super.onManagedUpdate(pSecondsElapsed);
                    }
                };
                PhysicsHandler handler = new PhysicsHandler(fireWave);
                fireWave.registerUpdateHandler(handler);
                handler.setVelocityY(-1500.0f);
                FishGameActivity.this.fireWaveSound.play();
                FishGameActivity.this.addEntiyOnUIThread(FishGameActivity.this.scene, fireWave);
            }
            FishGameActivity.this.totalCoin = FishGameActivity.this.totalCoin + FishGameActivity.this.stepTotal;
            FishGameActivity.this.stepTotal = 0;
            FishGameActivity.this.scene.setOnSceneTouchListener(FishGameActivity.this);
        }
    };
    private Runnable rShowRanklayout = new Runnable() {
        public void run() {
            FishGameActivity.this.scene.attachChild(FishGameActivity.this.startLayer);
            FishGameActivity.this.scene.registerTouchArea(FishGameActivity.this.icStartGame);
            FishGameActivity.this.scene.registerTouchArea(FishGameActivity.this.icEndGame);
            float f = ((float) FishGameActivity.this.getWindowManager().getDefaultDisplay().getWidth()) / 1080.0f;
            FishGameActivity.this.lv = new ListView(FishGameActivity.this);
            LayoutParams localObject1 = new LayoutParams((int) ((FishGameActivity.this.barStartTextureRegion.getWidth() * f) - FishGameActivity.ANGLE), (int) (((FishGameActivity.this.barStartTextureRegion.getHeight() / 7.0f) * 5.0f) * f));
            FishGameActivity.this.lv.setLayoutParams(localObject1);
            localObject1.gravity = 17;
            localObject1.topMargin = (int) (((FishGameActivity.this.barStartTextureRegion.getHeight() / 7.0f) / 2.0f) * f);
            FishGameActivity.this.getWindow().addContentView(FishGameActivity.this.lv, localObject1);
            FishGameActivity.this.lv.setDividerHeight(1);
            FishGameActivity.this.lv.setHeaderDividersEnabled(false);
            View header = View.inflate(FishGameActivity.this, C1680R.layout.layout_fish_rank_list_header, null);
            ImageView userHead = (ImageView) header.findViewById(C1680R.id.img_user_photo);
            TextView userName = (TextView) header.findViewById(C1680R.id.tv_user_name);
            final TextView userRank = (TextView) header.findViewById(C1680R.id.tv_rank);
            final TextView userScore = (TextView) header.findViewById(C1680R.id.tv_score);
            User user = (User) DroiUser.getCurrentUser(User.class);
            FishGameActivity.this.lv.addHeaderView(header);
            userName.setText(TextUtils.isEmpty(user.getNickName()) ? user.getUserId() : user.getNickName());
            Tools.displayFace(userHead, user.getHead());
            BaasHelper.getFishRankList(new DroiCallback<Response>() {
                public void result(Response response, DroiError droiError) {
                    if (droiError.isOk() && FishGameActivity.this.lv != null) {
                        FishGameActivity.this.lv.setAdapter(new MyAdapter(response.rankList));
                        userRank.setText(response.mUser.rank + "");
                        userScore.setText(response.mUser.score + "");
                    }
                }
            });
        }
    };
    private Scene scene;
    private int score = 0;
    private Font scoreAddFont;
    private Font scoreFont;
    private Text scoreText;
    private int second = -1;
    private SensorManager sensorManager;
    private Rectangle startLayer;
    private int stepTotal = 0;
    private Text timeText;
    private Timer timer = new Timer();
    private MyTimerTask timerTask = new MyTimerTask();
    private int totalCoin = 0;
    private boolean touchAble = true;

    class C17342 implements OnClickListener {

        class C17331 implements Runnable {
            C17331() {
            }

            public void run() {
                ((ViewGroup) FishGameActivity.this.lv.getParent()).removeView(FishGameActivity.this.lv);
                FishGameActivity.this.lv = null;
            }
        }

        C17342() {
        }

        public void onClick(ButtonSprite paramAnonymousButtonSprite, float paramAnonymousFloat1, float paramAnonymousFloat2) {
            if (FishGameActivity.this.gameScore.getTime() <= 0) {
                FishGameActivity.this.toastOnUIThread(FishGameActivity.this.getString(C1680R.string.game_num_use_end), 0);
                return;
            }
            FishGameActivity.this.removeEntityFromScene(FishGameActivity.this.startLayer);
            FishGameActivity.this.scene.unregisterTouchArea(FishGameActivity.this.icAgain);
            FishGameActivity.this.scene.unregisterTouchArea(FishGameActivity.this.icEnd);
            FishGameActivity.this.scene.unregisterTouchArea(FishGameActivity.this.icShare);
            FishGameActivity.this.scene.unregisterTouchArea(FishGameActivity.this.icStartGame);
            FishGameActivity.this.scene.unregisterTouchArea(FishGameActivity.this.icEndGame);
            if (FishGameActivity.this.lv != null) {
                FishGameActivity.this.runOnUiThread(new C17331());
            }
            FishGameActivity.this.second = 60;
            FishGameActivity.this.level = 0;
            FishGameActivity.this.score = 0;
            FishGameActivity.this.setTextOnUIThread(FishGameActivity.this.levelText, (FishGameActivity.this.level + 1) + "");
            FishGameActivity.this.setTextOnUIThread(FishGameActivity.this.scoreText, FishGameActivity.this.score + "");
            FishGameActivity.this.setTextOnUIThread(FishGameActivity.this.timeText, FishGameActivity.this.second + "");
            FishGameActivity.this.backGroundlayer.detachChildren();
            FishGameActivity.this.fishLayer.detachChildren();
            Random ran = new Random();
            FishGameActivity.this.fishList.clear();
            for (int fishLevel = 0; fishLevel < ((LevelInfo) FishGameActivity.this.levelList.get(FishGameActivity.this.level)).fishNum.length; fishLevel++) {
                for (int i = 0; i < ((LevelInfo) FishGameActivity.this.levelList.get(FishGameActivity.this.level)).fishNum[fishLevel]; i++) {
                    boolean z;
                    FishInfo fishInfo = (FishInfo) FishGameActivity.this.fishInfoList.get(fishLevel);
                    String str = FishGameActivity.TAG;
                    StringBuilder append = new StringBuilder().append("fishinfo:");
                    if (fishInfo == null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Log.i(str, append.append(z).append(" texture:").append(fishInfo.getFishTexture() == null).toString());
                    FishSprite fishSprite = new FishSprite(fishLevel, (float) (ran.nextInt(1080 - fishInfo.getWidth()) + (fishInfo.getWidth() / 2)), (float) ((ran.nextInt((1920 - fishInfo.getHeight()) - 750) - (fishInfo.getHeight() / 2)) + 250), fishInfo);
                    FishGameActivity.this.fishList.add(fishSprite);
                    FishGameActivity.this.fishLayer.attachChild(fishSprite);
                }
            }
            FishGameActivity.this.scene.setOnSceneTouchListener(FishGameActivity.this);
            FishGameActivity.this.handler.post(FishGameActivity.this.rRenewArrow);
        }
    }

    class C17363 implements OnClickListener {

        class C17351 implements DroiCallback<Boolean> {
            C17351() {
            }

            public void result(Boolean aBoolean, DroiError droiError) {
                FishGameActivity.this.checkGameInfo = true;
                if (droiError.isOk()) {
                    FishGameActivity.this.toastOnUIThread(FishGameActivity.this.getString(C1680R.string.game_save_success), 0);
                    FishGameActivity.this.removeEntityFromScene(FishGameActivity.this.pauseLayer);
                    FishGameActivity.this.runOnUiThread(FishGameActivity.this.rShowRanklayout);
                    return;
                }
                FishGameActivity.this.toastOnUIThread(FishGameActivity.this.getString(C1680R.string.game_save_fail), 0);
            }
        }

        C17363() {
        }

        public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
            if (FishGameActivity.this.checkGameInfo) {
                FishGameActivity.this.checkGameInfo = false;
                FishGameActivity.this.gameScore.saveInBackground(new C17351());
                return;
            }
            FishGameActivity.this.toastOnUIThread(FishGameActivity.this.getString(C1680R.string.game_update_score), 0);
        }
    }

    class C17374 implements OnClickListener {
        C17374() {
        }

        public void onClick(ButtonSprite paramAnonymousButtonSprite, float paramAnonymousFloat1, float paramAnonymousFloat2) {
            FishGameActivity.this.finish();
        }
    }

    class C17395 implements OnClickListener {

        class C17381 implements DroiCallback<Boolean> {
            C17381() {
            }

            public void result(Boolean aBoolean, DroiError droiError) {
                FishGameActivity.this.checkGameInfo = true;
                if (droiError.isOk()) {
                    FishGameActivity.this.toastOnUIThread(FishGameActivity.this.getString(C1680R.string.game_save_success), 0);
                    FishGameActivity.this.finish();
                    return;
                }
                FishGameActivity.this.toastOnUIThread(FishGameActivity.this.getString(C1680R.string.game_save_fail), 0);
            }
        }

        C17395() {
        }

        public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
            if (FishGameActivity.this.checkGameInfo) {
                FishGameActivity.this.checkGameInfo = false;
                FishGameActivity.this.gameScore.saveInBackground(new C17381());
                return;
            }
            FishGameActivity.this.toastOnUIThread(FishGameActivity.this.getString(C1680R.string.game_update_score), 0);
        }
    }

    class C17417 implements Runnable {
        C17417() {
        }

        public void run() {
            FishGameActivity.this.touchAble = true;
            ArrowSprite arrowSprite = new ArrowSprite(FishGameActivity.this.arrowType);
            arrowSprite.setRotationCenter(21.0f, 161.0f);
            arrowSprite.setRotation(FishGameActivity.this.gunSprite.getRotation());
            FishGameActivity.this.arrowSpriteTmp = arrowSprite;
            FishGameActivity.this.backGroundlayer.attachChild(arrowSprite);
        }
    }

    class C17428 implements Runnable {
        C17428() {
        }

        public void run() {
            boolean z;
            boolean z2 = true;
            Random ran = new Random();
            int fishLevel = FishGameActivity.this.checkFishNum();
            FishInfo fishInfo = (FishInfo) FishGameActivity.this.fishInfoList.get(fishLevel);
            String str = FishGameActivity.TAG;
            StringBuilder append = new StringBuilder().append("fishinfo:");
            if (fishInfo == null) {
                z = true;
            } else {
                z = false;
            }
            StringBuilder append2 = append.append(z).append(" texture:");
            if (fishInfo.getFishTexture() != null) {
                z2 = false;
            }
            Log.i(str, append2.append(z2).toString());
            FishSprite fishSprite = new FishSprite(fishLevel, 0.0f - fishInfo.getFishTexture().getWidth(), (float) ((ran.nextInt((1920 - fishInfo.getHeight()) - 750) - (fishInfo.getHeight() / 2)) + 250), fishInfo);
            FishGameActivity.this.fishList.add(fishSprite);
            FishGameActivity.this.fishLayer.attachChild(fishSprite);
        }
    }

    class C17439 implements Runnable {
        C17439() {
        }

        public void run() {
            FishGameActivity.this.arrowRenewDuration = FishGameActivity.ARROW_RENEW_DURATION;
            FishGameActivity.this.arrowType = 0;
        }
    }

    class ArrowSprite extends Sprite {
        ArrayList<FishSprite> hitFishList = new ArrayList();
        public PhysicsHandler mPhysicsHandler;
        int type = 0;

        ArrowSprite(int type) {
            super((1080.0f - ((ITextureRegion) FishGameActivity.this.arrowTextureRegionList.get(type)).getWidth()) / 2.0f, (1920.0f - ((ITextureRegion) FishGameActivity.this.arrowTextureRegionList.get(type)).getHeight()) - FishGameActivity.ARROW_PADDING_Y, (ITextureRegion) FishGameActivity.this.arrowTextureRegionList.get(type), FishGameActivity.this.getVertexBufferObjectManager());
            this.type = type;
            this.mPhysicsHandler = new PhysicsHandler(this);
            registerUpdateHandler(this.mPhysicsHandler);
            registerUpdateHandler(new IUpdateHandler(FishGameActivity.this) {
                public void onUpdate(float pSecondsElapsed) {
                    Iterator it = FishGameActivity.this.fishList.iterator();
                    while (it.hasNext()) {
                        FishSprite fishSprite = (FishSprite) it.next();
                        if (ArrowSprite.this.collidesWith(fishSprite)) {
                            if (!ArrowSprite.this.hitFishList.contains(fishSprite)) {
                                FishGameActivity.this.fishShotSound.play();
                                if (ArrowSprite.this.type == 1) {
                                    fishSprite.hit = 0;
                                } else {
                                    fishSprite.hit--;
                                }
                                ArrowSprite.this.hitFishList.add(fishSprite);
                                final FishSprite fishSprite2;
                                if (fishSprite.hit <= 0) {
                                    fishSprite.mPhysicsHandler.setVelocity(0.0f, 0.0f);
                                    fishSprite.mPhysicsHandler.setAcceleration(0.0f, 0.0f);
                                    FishGameActivity.this.score = FishGameActivity.this.score + fishSprite.getScore();
                                    if (FishGameActivity.this.score >= ((LevelInfo) FishGameActivity.this.levelList.get(FishGameActivity.this.level)).topScore) {
                                        Random ran = new Random();
                                        FishGameActivity.this.level = FishGameActivity.this.level + 1;
                                        FishGameActivity.this.levelText.setText((FishGameActivity.this.level + 1) + "");
                                        if (FishGameActivity.this.level % 2 == 0) {
                                            FishGameActivity.this.powerShow = 0;
                                            PowerUpSprite power = new PowerUpSprite(0.0f, (float) (ran.nextInt(1170) + 250), FishGameActivity.this.powerTextureRegionList.size() - 1);
                                            FishGameActivity.this.powerUpSpriteList.add(power);
                                            FishGameActivity.this.addEntiyOnUIThread(FishGameActivity.this.fishLayer, power);
                                        }
                                        FishGameActivity.this.second = FishGameActivity.this.second + 10;
                                        int count = FishGameActivity.this.fishList.size() - ((LevelInfo) FishGameActivity.this.levelList.get(FishGameActivity.this.level)).getFishCount();
                                        for (int i = 0; i < count; i++) {
                                            FishGameActivity.this.handler.post(FishGameActivity.this.rRenewFish);
                                        }
                                    }
                                    Log.i(FishGameActivity.TAG, "ah collisdeswish fish:" + FishGameActivity.this.score);
                                    FishGameActivity.this.setTextOnUIThread(FishGameActivity.this.scoreText, FishGameActivity.this.score + "");
                                    Text text = new Text(fishSprite.getX() + (fishSprite.getWidth() / 2.0f), fishSprite.getY(), FishGameActivity.this.scoreAddFont, "+" + fishSprite.getScore(), new TextOptions(HorizontalAlign.CENTER), FishGameActivity.this.getVertexBufferObjectManager());
                                    final Text text2 = text;
                                    text.registerEntityModifier(new MoveYModifier(1.0f, text.getY(), text.getY() - FishGameActivity.ANGLE) {
                                        protected void onModifierFinished(IEntity pItem) {
                                            FishGameActivity.this.removeEntityFromScene(text2);
                                            super.onModifierFinished(pItem);
                                        }
                                    });
                                    fishSprite.clearEntityModifiers();
                                    fishSprite2 = fishSprite;
                                    fishSprite.registerEntityModifier(new AlphaModifier(1.0f, FishGameActivity.SCORE_BAR_PADDING_X, 0.0f) {
                                        protected void onModifierFinished(IEntity pItem) {
                                            FishGameActivity.this.removeEntity(FishGameActivity.this.fishLayer, fishSprite2);
                                            super.onModifierFinished(pItem);
                                        }
                                    });
                                    text.setBlendFunction(IShape.BLENDFUNCTION_SOURCE_DEFAULT, 771);
                                    FishGameActivity.this.addEntiyOnUIThread(text);
                                    FishGameActivity.this.handler.postDelayed(FishGameActivity.this.rRenewFish, 3000);
                                    FishGameActivity.this.fishList.remove(fishSprite);
                                } else {
                                    fishSprite2 = fishSprite;
                                    fishSprite.registerEntityModifier(new AlphaModifier(1.0f, FishGameActivity.SCORE_BAR_PADDING_X, 0.0f) {
                                        protected void onModifierFinished(IEntity pItem) {
                                            super.onModifierFinished(pItem);
                                            fishSprite2.setAlpha(1.0f);
                                        }
                                    });
                                    fishSprite.animate(new long[]{250, 250}, 2, 3, true);
                                }
                                if (ArrowSprite.this.type != 2) {
                                    ArrowSprite.this.mPhysicsHandler.setVelocity(0.0f);
                                    FishGameActivity.this.removeArrowSprite(ArrowSprite.this);
                                }
                                int i2 = ArrowSprite.this.type;
                                return;
                            }
                            return;
                        }
                    }
                    it = FishGameActivity.this.powerUpSpriteList.iterator();
                    while (it.hasNext()) {
                        final PowerUpSprite powerUpSprite = (PowerUpSprite) it.next();
                        if (ArrowSprite.this.collidesWith(powerUpSprite)) {
                            FishGameActivity.this.handler.removeCallbacks(FishGameActivity.this.rRemovePower);
                            FishGameActivity.this.handler.postDelayed(FishGameActivity.this.rRemovePower, 6000);
                            ArrowSprite.this.mPhysicsHandler.setVelocity(0.0f);
                            FishGameActivity.this.removeArrowSprite(ArrowSprite.this);
                            FishGameActivity.this.powerUpSpriteList.remove(powerUpSprite);
                            powerUpSprite.registerEntityModifier(new AlphaModifier(1.0f, FishGameActivity.SCORE_BAR_PADDING_X, 0.0f) {
                                protected void onModifierFinished(IEntity pItem) {
                                    FishGameActivity.this.removeEntity(FishGameActivity.this.fishLayer, powerUpSprite);
                                    super.onModifierFinished(pItem);
                                }
                            });
                            switch (powerUpSprite.type) {
                                case 0:
                                    FishGameActivity.this.arrowRenewDuration = 150;
                                    return;
                                case 1:
                                    FishGameActivity.this.arrowType = 1;
                                    return;
                                case 2:
                                    FishGameActivity.this.arrowType = 2;
                                    return;
                                case 3:
                                    FishGameActivity.this.timerTask.cancel();
                                    FishGameActivity.this.scene.setOnSceneTouchListener(null);
                                    FishGameActivity.this.stepTotal = 0;
                                    FishGameActivity.this.setTextOnUIThread(FishGameActivity.this.coinNumText, FishGameActivity.this.stepTotal + "/" + 500);
                                    FishGameActivity.this.addEntiyOnUIThread(FishGameActivity.this.scene, FishGameActivity.this.boxLayer);
                                    FishGameActivity.this.coinNumText.setColor(Color.WHITE);
                                    FishGameActivity.this.inBoxLayer = true;
                                    FishGameActivity.this.handler.postDelayed(FishGameActivity.this.rResumeGameFromBox, 5000);
                                    return;
                                default:
                                    return;
                            }
                        }
                    }
                }

                public void reset() {
                }
            });
        }

        protected void onManagedUpdate(float pSecondsElapsed) {
            float xLocation;
            float radian = (float) (((double) (getRotation() / 180.0f)) * 3.141592653589793d);
            float yLocation;
            if (getRotation() > 0.0f) {
                xLocation = this.mX - (((float) Math.sin((double) radian)) * FishGameActivity.this.arrowTextureRegion.getHeight());
                yLocation = this.mY + (((float) Math.cos((double) radian)) * FishGameActivity.this.arrowTextureRegion.getHeight());
            } else {
                xLocation = this.mX + (((float) Math.sin((double) (-radian))) * FishGameActivity.this.arrowTextureRegion.getHeight());
                yLocation = this.mY + (((float) Math.cos((double) (-radian))) * FishGameActivity.this.arrowTextureRegion.getHeight());
            }
            if (xLocation < 0.0f || xLocation > 1080.0f || yLocation < 0.0f) {
                Log.i(FishGameActivity.TAG, "arrow out of bound");
                FishGameActivity.this.removeEntity(FishGameActivity.this.backGroundlayer, this);
            }
            super.onManagedUpdate(pSecondsElapsed);
        }

        public void onDetached() {
            Log.i(FishGameActivity.TAG, "arrow onDetached");
            super.onDetached();
            dispose();
        }
    }

    class FishInfo {
        String fileName;
        TiledTextureRegion fishTexture;
        int height;
        int hit;
        int score;
        int speed;
        int width;

        FishInfo() {
        }

        public int getHit() {
            return this.hit;
        }

        public void setHit(int hit) {
            this.hit = hit;
        }

        public int getWidth() {
            return this.width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public TiledTextureRegion getFishTexture() {
            return this.fishTexture;
        }

        public void setFishTexture(TiledTextureRegion fishTexture) {
            this.fishTexture = fishTexture;
        }

        public String getFileName() {
            return this.fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
            FishGameActivity.this.loadSourceImage(fileName);
        }

        public int getScore() {
            return this.score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSpeed() {
            return this.speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public void dealTexture() {
            if (this.hit == 1) {
                this.fishTexture = FishGameActivity.this.loadFishSourceImage(this.width, this.height, this.fileName, 2, 1);
            } else {
                this.fishTexture = FishGameActivity.this.loadFishSourceImage(this.width, this.height, this.fileName, 2, 2);
            }
        }
    }

    class FishSprite extends AnimatedSprite {
        int fishLevel;
        int hit;
        public PhysicsHandler mPhysicsHandler;
        int score;

        FishSprite(int fishLevel, float x, float y, FishInfo info) {
            super(x, y, info.getFishTexture(), FishGameActivity.this.getVertexBufferObjectManager());
            final Random ran = new Random();
            final FishGameActivity fishGameActivity = FishGameActivity.this;
            final FishInfo fishInfo = info;
            this.mPhysicsHandler = new PhysicsHandler(this) {
                protected void onUpdate(float pSecondsElapsed, IEntity pEntity) {
                    super.onUpdate(pSecondsElapsed, pEntity);
                    if (getVelocityX() > ((float) (fishInfo.getSpeed() * 2))) {
                        FishSprite.this.mPhysicsHandler.setAccelerationX((float) (-((fishInfo.getSpeed() * 2) + (((ran.nextInt(fishInfo.getSpeed()) / 10) / 3) * 2))));
                    } else if (getVelocityX() < ((float) ((fishInfo.getSpeed() / 3) * 2))) {
                        FishSprite.this.mPhysicsHandler.setAccelerationX((float) ((fishInfo.getSpeed() * 2) + (ran.nextInt(fishInfo.getSpeed()) / 10)));
                    }
                }
            };
            registerUpdateHandler(this.mPhysicsHandler);
            this.mPhysicsHandler.setVelocityX((float) (info.getSpeed() / 2));
            this.mPhysicsHandler.setAccelerationX((float) ((info.getSpeed() * 2) + (ran.nextInt(info.getSpeed()) / 10)));
            this.score = info.getScore();
            this.hit = info.getHit();
            animate(new long[]{250, 250}, 0, 1, true);
            setBlendFunction(IShape.BLENDFUNCTION_SOURCE_DEFAULT, 771);
            this.fishLevel = fishLevel;
        }

        public void setSpeed(float speed) {
            this.mPhysicsHandler.setVelocity(speed, 0.0f);
        }

        public float getSpeed() {
            return this.mPhysicsHandler.getVelocityX();
        }

        public int getScore() {
            return this.score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getFishLevel() {
            return this.fishLevel;
        }

        protected void onManagedUpdate(float pSecondsElapsed) {
            Random ran = new Random();
            if (this.mX > 1080.0f) {
                setPosition(-getWidth(), this.mY);
                this.mPhysicsHandler.setVelocityY(ran.nextBoolean() ? (float) ran.nextInt(getScore()) : (float) (-ran.nextInt(getScore())));
            }
            if (this.mY <= 250.0f || this.mY >= 1170.0f) {
                this.mPhysicsHandler.setVelocityY(-this.mPhysicsHandler.getVelocityY());
            }
            super.onManagedUpdate(pSecondsElapsed);
        }

        public void onDetached() {
            super.onDetached();
            dispose();
        }
    }

    class LevelInfo {
        int fishCount;
        int[] fishNum;
        int level;
        int topScore;

        LevelInfo(int r6, int r7, int[] r8) {
            /* JADX: method processing error */
/*
Error: java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
	at java.util.BitSet.get(BitSet.java:623)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.usedArgAssign(CodeShrinker.java:138)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.access$300(CodeShrinker.java:43)
	at jadx.core.dex.visitors.CodeShrinker.canMoveBetweenBlocks(CodeShrinker.java:282)
	at jadx.core.dex.visitors.CodeShrinker.shrinkBlock(CodeShrinker.java:230)
	at jadx.core.dex.visitors.CodeShrinker.shrinkMethod(CodeShrinker.java:38)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkArrayForEach(LoopRegionVisitor.java:196)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkForIndexedLoop(LoopRegionVisitor.java:119)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:65)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:52)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:46)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            com.zhuoyou.plugin.running.activity.FishGameActivity.this = r5;
            r4.<init>();
            r4.level = r6;
            r4.fishNum = r8;
            if (r8 == 0) goto L_0x0019;
        L_0x000b:
            r2 = r8.length;
            r1 = 0;
        L_0x000d:
            if (r1 >= r2) goto L_0x0019;
        L_0x000f:
            r0 = r8[r1];
            r3 = r4.fishCount;
            r3 = r3 + r0;
            r4.fishCount = r3;
            r1 = r1 + 1;
            goto L_0x000d;
        L_0x0019:
            r4.topScore = r7;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.running.activity.FishGameActivity.LevelInfo.<init>(com.zhuoyou.plugin.running.activity.FishGameActivity, int, int, int[]):void");
        }

        public int getFishCount() {
            return this.fishCount;
        }

        public int getFishNum(int fishLevel) {
            if (this.fishNum == null || this.fishNum.length <= 0 || fishLevel + 1 > this.fishNum.length) {
                return 0;
            }
            return this.fishNum[fishLevel];
        }
    }

    class MyAdapter extends BaseAdapter {
        private List<RankInfo> rankList = new ArrayList();

        class ViewHolder {
            ImageView imgPhoto;
            TextView tvName;
            TextView tvRank;
            TextView tvScore;

            ViewHolder() {
            }
        }

        MyAdapter(List<RankInfo> ranklist) {
            this.rankList = ranklist;
        }

        public int getCount() {
            Log.i("yzw", "size:" + this.rankList.size());
            return this.rankList.size();
        }

        public Object getItem(int paramInt) {
            return null;
        }

        public long getItemId(int paramInt) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null || convertView.getTag() == null) {
                holder = new ViewHolder();
                convertView = View.inflate(FishGameActivity.this, C1680R.layout.item_fish_rank, null);
                holder.imgPhoto = (ImageView) convertView.findViewById(C1680R.id.img_user_photo);
                holder.tvRank = (TextView) convertView.findViewById(C1680R.id.tv_rank);
                holder.tvName = (TextView) convertView.findViewById(C1680R.id.tv_user_name);
                holder.tvScore = (TextView) convertView.findViewById(C1680R.id.tv_score);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvRank.setText((((RankInfo) this.rankList.get(position)).rank + 1) + "");
            holder.tvScore.setText(((RankInfo) this.rankList.get(position)).score + "");
            if (!TextUtils.isEmpty(((RankInfo) this.rankList.get(position)).headUrl)) {
                Tools.displayImage(holder.imgPhoto, ((RankInfo) this.rankList.get(position)).headUrl, false);
            }
            holder.tvName.setText(TextUtils.isEmpty(((RankInfo) this.rankList.get(position)).user.getNickName()) ? ((RankInfo) this.rankList.get(position)).user.getUserId() : ((RankInfo) this.rankList.get(position)).user.getNickName());
            return convertView;
        }
    }

    class MyTimerTask extends TimerTask {

        class C17501 implements Runnable {
            C17501() {
            }

            public void run() {
                FishGameActivity.this.timeText.setText(FishGameActivity.this.second + "");
            }
        }

        MyTimerTask() {
        }

        public void run() {
            FishGameActivity.this.second = FishGameActivity.this.second - 1;
            FishGameActivity.this.powerShow = FishGameActivity.this.powerShow + 1;
            if (FishGameActivity.this.second >= 0) {
                FishGameActivity.this.runOnUpdateThread(new C17501());
                Random ran = new Random();
                if (FishGameActivity.this.arrowType == 0 && ran.nextInt(100) < FishGameActivity.this.powerShow) {
                    FishGameActivity.this.powerShow = 0;
                    FishGameActivity.this.handler.post(FishGameActivity.this.rBuildPower);
                }
            } else if (FishGameActivity.this.second == -1) {
                FishGameActivity.this.stopGame();
            }
        }
    }

    class PowerUpSprite extends Sprite {
        public PhysicsHandler mPhysicsHandler;
        int type = -1;

        PowerUpSprite(float x, float y, int type) {
            super(x, y, (ITextureRegion) FishGameActivity.this.powerTextureRegionList.get(type), FishGameActivity.this.getVertexBufferObjectManager());
            this.type = type;
            setScale(1.5f);
            this.mPhysicsHandler = new PhysicsHandler(this, FishGameActivity.this) {
                protected void onUpdate(float pSecondsElapsed, IEntity pEntity) {
                    super.onUpdate(pSecondsElapsed, pEntity);
                    if (getVelocityY() > 300.0f) {
                        setAccelerationY(-500.0f);
                    } else if (getVelocityY() < -300.0f) {
                        setAccelerationY(500.0f);
                    }
                }
            };
            registerUpdateHandler(this.mPhysicsHandler);
            this.mPhysicsHandler.setVelocityX(300.0f);
            this.mPhysicsHandler.setAccelerationY(500.0f);
        }

        protected void onManagedUpdate(float pSecondsElapsed) {
            super.onManagedUpdate(pSecondsElapsed);
            if (this.mX >= 1080.0f) {
                FishGameActivity.this.powerUpSpriteList.remove(this);
                FishGameActivity.this.removeEntity(FishGameActivity.this.fishLayer, this);
            }
        }
    }

    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float[] values = event.values;
        if (sensorType != 1) {
            return;
        }
        if ((Math.abs(values[0]) > 15.0f || Math.abs(values[1]) > 15.0f || Math.abs(values[2]) > 15.0f) && this.inBoxLayer) {
            ITextureRegion region;
            int step;
            Random ran = new Random();
            int value = ran.nextInt(60);
            this.coinSound.play();
            if (value >= 45) {
                region = this.coin2TextureRegion;
                step = 10;
            } else if (value >= 25) {
                region = this.coin1TextureRegion;
                step = 5;
            } else {
                region = this.coinTextureRegion;
                step = 1;
            }
            this.stepTotal += step;
            if (this.stepTotal >= 500) {
                this.coinNumText.setColor(Color.GREEN);
            } else {
                this.coinNumText.setColor(Color.WHITE);
            }
            setTextOnUIThread(this.coinNumText, this.stepTotal + "/" + 500);
            Sprite coin = new Sprite(540.0f, 960.0f - (this.boxBarTextureRegion.getHeight() / 4.0f), region, getVertexBufferObjectManager()) {
                protected void onManagedUpdate(float pSecondsElapsed) {
                    super.onManagedUpdate(pSecondsElapsed);
                    if (this.mY > 1920.0f) {
                        FishGameActivity.this.removeEntity(FishGameActivity.this.scene, this);
                        Log.i(FishGameActivity.TAG, "stepTotal:" + FishGameActivity.this.stepTotal);
                    }
                }
            };
            coin.registerEntityModifier(new LoopEntityModifier(new RotationModifier(((float) ran.nextInt(3)) + ran.nextFloat(), 0.0f, 360.0f)));
            PhysicsHandler handler = new PhysicsHandler(coin);
            coin.registerUpdateHandler(handler);
            handler.setVelocityX(ran.nextBoolean() ? (float) ran.nextInt(500) : (float) (-ran.nextInt(500)));
            handler.setVelocityY((float) ((-ran.nextInt(1000)) - 200));
            handler.setAccelerationY(1000.0f);
            addEntiyOnUIThread(this.scene, coin);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onBackPressed() {
        finish();
    }

    private void stopGame() {
        this.handler.removeCallbacks(this.rRenewArrow);
        this.touchAble = false;
        this.inBoxLayer = false;
        this.arrowType = 0;
        removeEntity(this.scene, this.boxLayer);
        addEntiyOnUIThread(this.pauseLayer);
        setTextOnUIThread(this.endScoreText, this.score + "");
        setTextOnUIThread(this.endLevelText, (this.level + 1) + "");
        this.scene.registerTouchArea(this.icAgain);
        this.scene.registerTouchArea(this.icEnd);
        this.scene.registerTouchArea(this.icShare);
        this.scene.setOnSceneTouchListener(null);
        if (!Tools.getToday().equals(this.gameScore.getDate())) {
            this.gameScore = new FishGameScore();
            this.gameScore.setTime(3);
        }
        this.gameScore.setAccount(DroiUser.getCurrentUser().getUserId());
        this.gameScore.setDate(Tools.getToday());
        this.gameScore.setTime(this.gameScore.getTime() - 1);
        this.gameScore.setTopScore(this.gameScore.getTopScore() > this.score ? this.gameScore.getTopScore() : this.score);
        this.gameScore.setTopLevel(this.gameScore.getTopLevel() > this.level + 1 ? this.gameScore.getTopLevel() : this.level + 1);
        this.gameScore.setScore(this.gameScore.getScore() + this.score);
        this.gameScore.setCoin(this.gameScore.getCoin() + this.totalCoin);
        this.totalCoin = 0;
    }

    public EngineOptions onCreateEngineOptions() {
        Log.i(TAG, "onCreateEngineOptions");
        Camera camera = new Camera(0.0f, 0.0f, 1080.0f, 1920.0f);
        this.sensorManager = (SensorManager) getSystemService("sensor");
        EngineOptions option = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(1080.0f, 1920.0f), camera);
        option.getAudioOptions().setNeedsSound(true);
        return option;
    }

    protected synchronized void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this, this.sensorManager.getDefaultSensor(1), 1);
    }

    protected void onPause() {
        super.onPause();
        this.sensorManager.unregisterListener(this);
    }

    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
        Log.i(TAG, "onCreateResources");
        this.gameScore = (FishGameScore) getIntent().getParcelableExtra("fishScore");
        this.background = new RepeatingSpriteBackground(1080.0f, 1920.0f, getTextureManager(), AssetBitmapTextureAtlasSource.create(getAssets(), PATH_BACKGROUND), getVertexBufferObjectManager());
        this.bottomTextureRegion = loadSourceImage(PATH_BOTTOM);
        this.barTextureRegion = loadSourceImage(PATH_BAR);
        this.barScoreTextureRegion = loadSourceImage(PATH_BAR_SCORE);
        this.barTimerTextureRegion = loadSourceImage(PATH_BAR_TIMER);
        this.barEndTextureRegion = loadSourceImage(PATH_END_BAR);
        this.boxBarTextureRegion = loadSourceImage(PATH_BOX_BAR);
        this.icEndTextureRegion = loadSourceImage(PATH_IC_END);
        this.icAgainTextureRegion = loadSourceImage(PATH_IC_AGAIN);
        this.icShareEndTextureRegion = loadSourceImage(PATH_IC_SHARE);
        this.boxTextureRegion = loadSourceImage(PATH_BOX);
        this.coinTextureRegion = loadSourceImage(PATH_BOX_COIN);
        this.coin1TextureRegion = loadSourceImage(PATH_BOX_COIN_1);
        this.coin2TextureRegion = loadSourceImage(PATH_BOX_COIN_2);
        this.barStartTextureRegion = loadSourceImage(PATH_START_BAR);
        this.icStartGameTextureRegion = loadSourceImage(PATH_IC_START_GAME);
        this.fireWaveTextureRegion = loadSourceImage(PATH_FIRE_WAVE);
        this.powerSpeedTextureRegion = loadSourceImage(PATH_POWER_SPEED);
        this.powerHeavyTextureRegion = loadSourceImage(PATH_POWER_HEAVY);
        this.powerThroughTextureRegion = loadSourceImage(PATH_POWER_THROUGH);
        this.powerTextureRegionList.add(this.powerSpeedTextureRegion);
        this.powerTextureRegionList.add(this.powerHeavyTextureRegion);
        this.powerTextureRegionList.add(this.powerThroughTextureRegion);
        this.powerTextureRegionList.add(this.boxTextureRegion);
        this.arrowTextureRegion = loadSourceImage(PATH_ARROW);
        this.arrowHeavyTextureRegion = loadSourceImage(PATH_ARROW_HEAVY);
        this.arrowThroughTextureRegion = loadSourceImage(PATH_ARROW_THROUGH);
        this.arrowTextureRegionList.add(this.arrowTextureRegion);
        this.arrowTextureRegionList.add(this.arrowHeavyTextureRegion);
        this.arrowTextureRegionList.add(this.arrowThroughTextureRegion);
        this.gunTextureRegion = loadSourceImage(PATH_GUN);
        dealLevelXml(C1680R.xml.level_1);
        this.scoreFont = FontFactory.create(getFontManager(), getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, 3), (float) SCORE_BAR_PADDING_Y, Color.WHITE.getABGRPackedInt());
        this.scoreFont.load();
        this.scoreAddFont = FontFactory.create(getFontManager(), getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, 3), (float) SCORE_BAR_PADDING_Y, Color.WHITE.getABGRPackedInt());
        this.scoreAddFont.load();
        this.levelFont = FontFactory.create(getFontManager(), getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, 1), 80.0f, Color.WHITE.getABGRPackedInt());
        this.levelFont.load();
        LevelInfo level_1 = new LevelInfo(0, 100, new int[]{8});
        LevelInfo level_2 = new LevelInfo(0, HttpResponseCode.BAD_REQUEST, new int[]{6, 2});
        LevelInfo level_3 = new LevelInfo(0, 800, new int[]{4, 4, 2});
        LevelInfo level_4 = new LevelInfo(0, 1400, new int[]{4, 3, 2, 1});
        LevelInfo level_5 = new LevelInfo(0, 2100, new int[]{2, 4, 2, 1});
        LevelInfo level_6 = new LevelInfo(0, MessageHandler.WHAT_ITEM_SELECTED, new int[]{1, 3, 3, 2});
        LevelInfo levelInfo = new LevelInfo(0, m_AppUI.MSG_APP_SAVESCREEN, new int[]{1, 4, 4, 2});
        levelInfo = new LevelInfo(0, 5500, new int[]{4, 4, 4, 3});
        levelInfo = new LevelInfo(0, 7000, new int[]{2, 5, 5, 4});
        levelInfo = new LevelInfo(0, Rank.MIN, new int[]{5, 5, 5, 5});
        this.levelList.add(level_1);
        this.levelList.add(level_2);
        this.levelList.add(level_3);
        this.levelList.add(level_4);
        this.levelList.add(level_5);
        this.levelList.add(level_6);
        this.levelList.add(levelInfo);
        this.levelList.add(levelInfo);
        this.levelList.add(levelInfo);
        this.levelList.add(levelInfo);
        this.arrowNormalSound = SoundFactory.createSoundFromAsset(getSoundManager(), this, PATH_SOUND_ARROW_NORMAL);
        this.arrowHeavySound = SoundFactory.createSoundFromAsset(getSoundManager(), this, PATH_SOUND_ARROW_HEAVY);
        this.arrowThroughSound = SoundFactory.createSoundFromAsset(getSoundManager(), this, PATH_SOUND_ARROW_THROUGH);
        this.fishShotSound = SoundFactory.createSoundFromAsset(getSoundManager(), this, PATH_SOUND_FISH_SHOT);
        this.coinSound = SoundFactory.createSoundFromAsset(getSoundManager(), this, PATH_SOUND_COIN);
        this.fireWaveSound = SoundFactory.createSoundFromAsset(getSoundManager(), this, PATH_SOUND_FIRE_WAVE);
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        Log.i(TAG, "onCreateScene");
        this.mEngine.registerUpdateHandler(new FPSLogger());
        this.scene = new Scene();
        Sprite sprite = new Sprite(0.0f, 1920.0f - this.bottomTextureRegion.getHeight(), this.bottomTextureRegion, getVertexBufferObjectManager());
        sprite = new Sprite((float) SCORE_BAR_PADDING_X, (float) SCORE_BAR_PADDING_Y, this.barTextureRegion, getVertexBufferObjectManager());
        sprite = new Sprite((float) TIMER_BAR_PADDING_X, (float) SCORE_BAR_PADDING_Y, this.barTextureRegion, getVertexBufferObjectManager());
        sprite = new Sprite(SCORE_BAR_PADDING_X - (this.barScoreTextureRegion.getWidth() / 2.0f), SCORE_BAR_PADDING_Y - ((this.barScoreTextureRegion.getHeight() / 2.0f) - (this.barTextureRegion.getHeight() / 2.0f)), this.barScoreTextureRegion, getVertexBufferObjectManager());
        sprite = new Sprite(TIMER_BAR_PADDING_X - (this.barScoreTextureRegion.getWidth() / 2.0f), SCORE_BAR_PADDING_Y - ((this.barScoreTextureRegion.getHeight() / 2.0f) - (this.barTextureRegion.getHeight() / 2.0f)), this.barTimerTextureRegion, getVertexBufferObjectManager());
        sprite = new Sprite(540.0f - (this.barEndTextureRegion.getWidth() / 2.0f), 960.0f - (this.barEndTextureRegion.getHeight() / 2.0f), this.barEndTextureRegion, getVertexBufferObjectManager());
        sprite = new Sprite(540.0f - (this.boxBarTextureRegion.getWidth() / 2.0f), 960.0f - (this.boxBarTextureRegion.getHeight() / 2.0f), this.boxBarTextureRegion, getVertexBufferObjectManager());
        sprite = new Sprite(540.0f - (this.barStartTextureRegion.getWidth() / 2.0f), 960.0f - (this.barStartTextureRegion.getHeight() / 2.0f), this.barStartTextureRegion, getVertexBufferObjectManager());
        this.icStartGame = new ButtonSprite(540.0f - (this.icStartGameTextureRegion.getWidth() / 2.0f), (960.0f + (this.barStartTextureRegion.getHeight() / 2.0f)) - (this.icStartGameTextureRegion.getHeight() / 2.0f), this.icStartGameTextureRegion, getVertexBufferObjectManager(), new C17342());
        this.icAgain = new ButtonSprite(540.0f - (this.icAgainTextureRegion.getWidth() / 2.0f), (960.0f + (this.barEndTextureRegion.getHeight() / 2.0f)) - (this.icShareEndTextureRegion.getHeight() / 2.0f), this.icAgainTextureRegion, getVertexBufferObjectManager(), new C17363());
        this.icEndGame = new ButtonSprite((float) SCORE_BAR_PADDING_Y, (float) SCORE_BAR_PADDING_Y, this.icEndTextureRegion, getVertexBufferObjectManager(), new C17374());
        this.icEnd = new ButtonSprite((this.icAgain.getX() - SCORE_BAR_PADDING_Y) - this.icEndTextureRegion.getWidth(), this.icAgain.getY(), this.icEndTextureRegion, getVertexBufferObjectManager(), new C17395());
        this.icShare = new ButtonSprite((this.icAgain.getX() + SCORE_BAR_PADDING_Y) + this.icShareEndTextureRegion.getWidth(), this.icAgain.getY(), this.icShareEndTextureRegion, getVertexBufferObjectManager());
        TextOptions scoreTextOptions = new TextOptions(HorizontalAlign.RIGHT);
        scoreTextOptions.setAutoWrap(AutoWrap.LETTERS);
        scoreTextOptions.setAutoWrapWidth((this.barTextureRegion.getWidth() - (this.barScoreTextureRegion.getWidth() / 2.0f)) - TitleBar.BACKBTN_LEFT_MARGIN);
        TextOptions textOptions = new TextOptions(HorizontalAlign.RIGHT);
        textOptions.setAutoWrap(AutoWrap.LETTERS);
        textOptions.setAutoWrapWidth(400.0f);
        textOptions = new TextOptions(HorizontalAlign.CENTER);
        textOptions.setAutoWrap(AutoWrap.LETTERS);
        textOptions.setAutoWrapWidth(400.0f);
        this.scoreText = new Text(SCORE_BAR_PADDING_X + (this.barScoreTextureRegion.getWidth() / 2.0f), SCORE_BAR_PADDING_Y + ((this.barTextureRegion.getHeight() / 2.0f) - 25.0f), this.scoreFont, "      " + this.score, scoreTextOptions, getVertexBufferObjectManager());
        this.timeText = new Text(TIMER_BAR_PADDING_X + (this.barScoreTextureRegion.getWidth() / 2.0f), SCORE_BAR_PADDING_Y + ((this.barTextureRegion.getHeight() / 2.0f) - 25.0f), this.scoreFont, (CharSequence) "     0", scoreTextOptions, getVertexBufferObjectManager());
        this.levelText = new Text(80.0f, (SCORE_BAR_PADDING_Y + ((this.barTextureRegion.getHeight() / 2.0f) - 40.0f)) - 5.0f, this.levelFont, (this.level + 1) + "", getVertexBufferObjectManager());
        this.endScoreText = new Text(330.0f, 288.0f, this.levelFont, (CharSequence) "      0", textOptions, getVertexBufferObjectManager());
        this.endLevelText = new Text(325.0f, 410.0f, this.levelFont, (CharSequence) "      0", textOptions, getVertexBufferObjectManager());
        this.coinNumText = new Text(440.0f, (960.0f + (sprite.getHeight() / 4.0f)) + SCORE_BAR_PADDING_Y, this.scoreFont, (CharSequence) "5000/500", textOptions, getVertexBufferObjectManager());
        ArrowSprite arrowSprite = new ArrowSprite(this.arrowType);
        arrowSprite.setRotationCenter(21.0f, 161.0f);
        this.arrowSpriteTmp = arrowSprite;
        this.gunSprite = new Sprite((1080.0f - this.gunTextureRegion.getWidth()) / 2.0f, (1920.0f - this.gunTextureRegion.getHeight()) + GUN_PADDING_Y, this.gunTextureRegion, getVertexBufferObjectManager());
        this.pauseLayer = new Rectangle(0.0f, 0.0f, 1080.0f, 1920.0f, getVertexBufferObjectManager());
        this.pauseLayer.setColor(Color.BLACK);
        this.pauseLayer.setAlpha(0.5f);
        this.pauseLayer.attachChild(sprite);
        this.pauseLayer.attachChild(this.icAgain);
        this.pauseLayer.attachChild(this.icEnd);
        this.pauseLayer.attachChild(this.icShare);
        this.boxLayer = new Rectangle(0.0f, 0.0f, 1080.0f, 1920.0f, getVertexBufferObjectManager());
        this.boxLayer.setColor(Color.BLACK);
        this.boxLayer.setAlpha(0.5f);
        this.boxLayer.attachChild(sprite);
        this.boxLayer.attachChild(this.coinNumText);
        sprite.attachChild(this.endScoreText);
        sprite.attachChild(this.endLevelText);
        this.startLayer = new Rectangle(0.0f, 0.0f, 1080.0f, 1920.0f, getVertexBufferObjectManager());
        this.startLayer.setColor(Color.BLACK);
        this.startLayer.setAlpha(0.5f);
        this.startLayer.attachChild(sprite);
        this.startLayer.attachChild(this.icStartGame);
        this.startLayer.attachChild(this.icEndGame);
        this.scene.setBackground(this.background);
        this.scene.attachChild(this.backGroundlayer);
        this.scene.attachChild(this.fishLayer);
        this.scene.attachChild(sprite);
        this.scene.attachChild(sprite);
        this.scene.attachChild(sprite);
        this.scene.attachChild(this.levelText);
        this.scene.attachChild(sprite);
        this.scene.attachChild(sprite);
        this.scene.attachChild(this.scoreText);
        this.scene.attachChild(this.timeText);
        this.backGroundlayer.attachChild(arrowSprite);
        this.scene.attachChild(this.gunSprite);
        this.scene.registerTouchArea(this.gunSprite);
        Random ran = new Random();
        for (int fishLevel = 0; fishLevel < ((LevelInfo) this.levelList.get(this.level)).fishNum.length; fishLevel++) {
            for (int i = 0; i < ((LevelInfo) this.levelList.get(this.level)).fishNum[fishLevel]; i++) {
                FishInfo fishInfo = (FishInfo) this.fishInfoList.get(fishLevel);
                Log.i(TAG, "fishinfo:" + (fishInfo == null) + " texture:" + (fishInfo.getFishTexture() == null));
                FishSprite fishSprite = new FishSprite(fishLevel, (float) (ran.nextInt(1080 - fishInfo.getWidth()) + (fishInfo.getWidth() / 2)), (float) ((ran.nextInt((1920 - fishInfo.getHeight()) - 750) - (fishInfo.getHeight() / 2)) + 250), fishInfo);
                this.fishList.add(fishSprite);
                this.fishLayer.attachChild(fishSprite);
            }
        }
        this.timer.schedule(this.timerTask, 1000, 1000);
        pOnCreateSceneCallback.onCreateSceneFinished(this.scene);
    }

    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        Log.i(TAG, "onPopulateScene");
        runOnUiThread(this.rShowRanklayout);
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    private TiledTextureRegion loadSourceImage(int width, int height, String imagePath) {
        Log.i(TAG, "loadSourceImage w:" + width + " h:" + height + " path:" + imagePath);
        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(getTextureManager(), width, height, TextureOptions.DEFAULT);
        TiledTextureRegion texture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, (Context) this, imagePath, 0, 0, 1, 1);
        textureAtlas.load();
        return texture;
    }

    private TiledTextureRegion loadFishSourceImage(int width, int height, String imagePath, int column, int row) {
        Log.i(TAG, "loadSourceImage w:" + width + " h:" + height + " path:" + imagePath);
        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(getTextureManager(), width, height, TextureOptions.BILINEAR);
        TiledTextureRegion texture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, (Context) this, imagePath, 0, 0, column, row);
        textureAtlas.load();
        return texture;
    }

    private ITextureRegion loadSourceImage(final String imagePath) {
        try {
            BitmapTexture mTexture = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                public InputStream open() throws IOException {
                    return FishGameActivity.this.getAssets().open(imagePath);
                }
            });
            mTexture.load();
            return TextureRegionFactory.extractFromTexture(mTexture);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "error:" + e.getMessage());
            return null;
        }
    }

    private void removeEntity(final Entity entity, final Entity sprite) {
        runOnUpdateThread(new Runnable() {
            public void run() {
                entity.detachChild(sprite);
            }
        });
    }

    private void removeEntityFromScene(Entity sprite) {
        removeEntity(this.scene, sprite);
    }

    private void removeArrowSprite(ArrowSprite sprite) {
        removeEntity(this.backGroundlayer, sprite);
    }

    private void setTextOnUIThread(final Text text, final String str) {
        runOnUpdateThread(new Runnable() {
            public void run() {
                text.setText(str);
            }
        });
    }

    private void addEntiyOnUIThread(final Entity entity) {
        runOnUpdateThread(new Runnable() {
            public void run() {
                FishGameActivity.this.scene.attachChild(entity);
            }
        });
    }

    private void addEntiyOnUIThread(final Entity parent, final Entity child) {
        runOnUpdateThread(new Runnable() {
            public void run() {
                parent.attachChild(child);
            }
        });
    }

    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        float x;
        float angle;
        float y = (this.gunSprite.getY() + this.gunSprite.getRotationCenterY()) - pSceneTouchEvent.getY();
        if (pSceneTouchEvent.getX() > 540.0f) {
            x = pSceneTouchEvent.getX() - (this.gunSprite.getX() + this.gunSprite.getRotationCenterX());
            angle = (float) (90.0d - ((Math.atan((double) (y / x)) / 3.141592653589793d) * 180.0d));
        } else {
            x = (this.gunSprite.getX() + this.gunSprite.getRotationCenterX()) - pSceneTouchEvent.getX();
            angle = -((float) (90.0d - ((Math.atan((double) (y / x)) / 3.141592653589793d) * 180.0d)));
        }
        this.gunSprite.setRotation(angle);
        if (this.touchAble) {
            float xSpeed;
            float ySpeed;
            this.touchAble = false;
            this.arrowSpriteTmp.setRotation(angle);
            this.handler.postDelayed(this.rRenewArrow, this.arrowRenewDuration);
            float radian = (float) (((double) (this.arrowSpriteTmp.getRotation() / 180.0f)) * 3.141592653589793d);
            if (this.arrowSpriteTmp.getRotation() > 0.0f) {
                xSpeed = ARROW_SPEED * ((float) Math.sin((double) radian));
                ySpeed = -2000.0f * ((float) Math.cos((double) radian));
            } else {
                xSpeed = -2000.0f * ((float) Math.sin((double) (-radian)));
                ySpeed = -2000.0f * ((float) Math.cos((double) (-radian)));
            }
            Log.i(TAG, "gunSprite move x:" + xSpeed + " y:" + ySpeed);
            this.arrowSpriteTmp.mPhysicsHandler.setVelocity(xSpeed, ySpeed);
            switch (this.arrowSpriteTmp.type) {
                case 0:
                    this.arrowNormalSound.play();
                    break;
                case 1:
                    this.arrowHeavySound.play();
                    break;
                case 2:
                    this.arrowThroughSound.play();
                    break;
            }
        }
        Log.i(TAG, "onSceneTouchEvent angle:" + angle + " x:" + x + " y:" + y);
        return false;
    }

    protected void onDestroy() {
        this.timerTask.cancel();
        this.handler.removeCallbacks(this.rBuildPower);
        this.handler.removeCallbacks(this.rRenewArrow);
        this.handler.removeCallbacks(this.rRenewFish);
        super.onDestroy();
    }

    private void dealLevelXml(int xmlId) {
        XmlResourceParser xml = getResources().getXml(xmlId);
        try {
            FishInfo fish = null;
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                switch (eventType) {
                    case 2:
                        String tag = xml.getName();
                        if (!tag.equalsIgnoreCase(TAG_FISH)) {
                            if (!tag.equalsIgnoreCase(TAG_FILENAME)) {
                                if (!tag.equalsIgnoreCase(TAG_SCORE)) {
                                    if (!tag.equalsIgnoreCase(TAG_SEPPD)) {
                                        if (!tag.equalsIgnoreCase("width")) {
                                            if (!tag.equalsIgnoreCase("height")) {
                                                if (!tag.equalsIgnoreCase(TAG_HIT)) {
                                                    break;
                                                }
                                                fish.setHit(Integer.valueOf(xml.nextText()).intValue());
                                                break;
                                            }
                                            fish.setHeight(Integer.valueOf(xml.nextText()).intValue());
                                            break;
                                        }
                                        fish.setWidth(Integer.valueOf(xml.nextText()).intValue());
                                        break;
                                    }
                                    fish.setSpeed(Integer.valueOf(xml.nextText()).intValue());
                                    break;
                                }
                                fish.setScore(Integer.valueOf(xml.nextText()).intValue());
                                break;
                            }
                            fish.setFileName(BASE_PATH + xml.nextText());
                            break;
                        }
                        fish = new FishInfo();
                        break;
                    case 3:
                        if (xml.getName().equalsIgnoreCase(TAG_FISH) && fish != null) {
                            fish.dealTexture();
                            this.fishInfoList.add(fish);
                            fish = null;
                            break;
                        }
                    default:
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private int checkFishNum() {
        int[] fishNum = new int[this.fishInfoList.size()];
        Iterator it = this.fishList.iterator();
        while (it.hasNext()) {
            int i = ((FishSprite) it.next()).fishLevel;
            fishNum[i] = fishNum[i] + 1;
        }
        for (int i2 = fishNum.length - 1; i2 >= 0; i2--) {
            if (((LevelInfo) this.levelList.get(this.level)).getFishNum(i2) > fishNum[i2]) {
                Log.i(TAG, "should renew fish:" + i2);
                return i2;
            }
        }
        return 0;
    }
}
