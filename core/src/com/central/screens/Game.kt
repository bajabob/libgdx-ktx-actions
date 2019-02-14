package com.central.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.*
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.central.Application
import ktx.app.KtxScreen
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction




class Game(val application: Application) : KtxScreen {

    private lateinit var stage: Stage
    private val width = Gdx.graphics.width.toFloat()
    private val height = Gdx.graphics.height.toFloat()
    private var lapsCount: Int = 0

    override fun show() {
        super.show()

        lapsCount = 3

        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
        val mySkin = Skin(Gdx.files.internal("skin/glassy-ui.json"))
        val texture = Texture(Gdx.files.internal("image.jpg"))

        val X_left = Gdx.graphics.width / 3 - texture.width / 2f
        val X_right = Gdx.graphics.width * 2 / 3 - texture.width / 2f
        val Y_top = Gdx.graphics.height * 2 / 3 - texture.height / 2f
        val Y_bottom = Gdx.graphics.height / 3 - texture.height / 2f

        val topLeftRightParallelAction = ParallelAction()
        topLeftRightParallelAction.addAction(Actions.moveTo(X_right.toFloat(), Y_top.toFloat(), 1f, Interpolation.exp5Out))
        topLeftRightParallelAction.addAction(Actions.scaleTo(0.75f, 0.75f, 1f, Interpolation.exp5Out))

        val moveBottomRightAction = MoveToAction()
        moveBottomRightAction.setPosition(X_right.toFloat(), Y_bottom.toFloat())
        moveBottomRightAction.duration = 1f
        moveBottomRightAction.interpolation = Interpolation.smooth

        val bottomLeftRightParallelAction = ParallelAction()
        bottomLeftRightParallelAction.addAction(Actions.moveTo(X_left.toFloat(), Y_bottom.toFloat(), 1f, Interpolation.sineOut))
        bottomLeftRightParallelAction.addAction(Actions.scaleTo(1f, 1f, 1f))

        val leftBottomTopParallelAction = ParallelAction()
        leftBottomTopParallelAction.addAction(Actions.moveTo(X_left.toFloat(), Y_top.toFloat(), 1f, Interpolation.swingOut))
        leftBottomTopParallelAction.addAction(Actions.rotateBy(90f, 1f))

        val lapsLabel = Label("", mySkin, "big-black")
        lapsLabel.setPosition(0f, 0f)
        lapsLabel.setSize(width, height)
        lapsLabel.setAlignment(Align.center)

        val lapsLabelContainer = Group()
        lapsLabelContainer.addActor(lapsLabel)
        lapsLabelContainer.setOrigin(lapsLabel.getWidth() / 2, lapsLabel.getHeight() / 2)

        val updateLapCountAction = RunnableAction()
        updateLapCountAction.runnable = Runnable {
            lapsCount--

            lapsLabelContainer.setScale(0f)
            val fadingSequenceAction = SequenceAction()
            fadingSequenceAction.addAction(Actions.fadeIn(1f))

            val parallelAction = ParallelAction()

            lapsLabel.setText(" Laps : " + (lapsCount + 1))
            fadingSequenceAction.addAction(Actions.fadeOut(2f))
            parallelAction.addAction(Actions.scaleBy(5f, 5f, 4f))

            parallelAction.addAction(fadingSequenceAction)
            lapsLabelContainer.addAction(parallelAction)
        }

        stage.addActor(lapsLabelContainer);

        val overallSequence1 = SequenceAction()
        overallSequence1.addAction(topLeftRightParallelAction)
        overallSequence1.addAction(moveBottomRightAction)
        overallSequence1.addAction(bottomLeftRightParallelAction)
        overallSequence1.addAction(leftBottomTopParallelAction)
        overallSequence1.addAction(updateLapCountAction)

        val image1 = Image(texture)
        image1.setPosition(X_left, Y_top)
        image1.setOrigin(image1.getWidth() / 2, image1.getHeight() / 2)
        stage.addActor(image1)

        fun createSpinner(x: Float, y: Float, rotationAmount: Float) {
            val leftBottomTopParallelAction2 = ParallelAction()
            leftBottomTopParallelAction2.addAction(Actions.rotateBy(rotationAmount, 1f))

            val i = Image(texture)
            i.scaleBy(-0.5f)
            i.setPosition(x, y)
            i.setOrigin(i.getWidth() / 2, i.getHeight() / 2)
            stage.addActor(i)

            val overallSequence2 = SequenceAction()
            overallSequence2.addAction(leftBottomTopParallelAction2)

            val loop = RepeatAction()
            loop.count = RepeatAction.FOREVER
            loop.action = overallSequence2
            i.addAction(loop)
        }

        createSpinner(X_left, Y_bottom, 360f)
        createSpinner(X_right, Y_bottom, -360f)
        createSpinner(X_right, Y_top, -360f)
        createSpinner(X_left, Y_top, 360f)

        val loopNbrAction = RepeatAction()
        loopNbrAction.count = lapsCount
        loopNbrAction.action = overallSequence1

        val completedAction = RunnableAction()
        completedAction.runnable = Runnable {
            lapsLabelContainer.setScale(0f)
            val fadingSequenceAction = SequenceAction()
            fadingSequenceAction.addAction(Actions.fadeIn(1f))

            val parallelAction = ParallelAction()

            lapsLabel.setText(" Finished!")
            //FadingSequenceAction.addAction(Actions.fadeOut(10));
            parallelAction.addAction(Actions.rotateBy(360f, 2f))
            parallelAction.addAction(Actions.scaleBy(1.2.toFloat(), 1.2.toFloat(), 3f, Interpolation.bounce))

            parallelAction.addAction(fadingSequenceAction)
            lapsLabelContainer.addAction(parallelAction)
        }

        image1.addAction(Actions.sequence(loopNbrAction, completedAction))

        // Button
        val button1 = TextButton("SPIN!", mySkin, "small")
        button1.setSize(200f, 50f)
        button1.setPosition(width/2f, height/4f, Align.center)
        button1.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                image1.clearActions()
                image1.addAction(Actions.sequence(loopNbrAction, completedAction))
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                image1.clearActions()
                val leftBottomTopParallelAction2 = ParallelAction()
                leftBottomTopParallelAction2.addAction(Actions.rotateBy(-360f, 0.5f))
                image1.addAction(leftBottomTopParallelAction2)
                return true
            }
        })
        stage.addActor(button1)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act()
        stage.draw()
    }
}
