package com.central.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
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

class Game(val application: Application) : KtxScreen {

    private lateinit var stage: Stage
    private val width = Gdx.graphics.width.toFloat()
    private val height = Gdx.graphics.height.toFloat()

    override fun show() {
        super.show()

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
        topLeftRightParallelAction.addAction(Actions.scaleTo(2f, 2f, 1f, Interpolation.exp5Out))

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

        val overallSequence1 = SequenceAction()
        overallSequence1.addAction(topLeftRightParallelAction)
        overallSequence1.addAction(moveBottomRightAction)
        overallSequence1.addAction(bottomLeftRightParallelAction)
        overallSequence1.addAction(leftBottomTopParallelAction)

        val image1 = Image(texture)
        image1.setPosition(X_left, Y_top)
        image1.setOrigin(image1.getWidth() / 2, image1.getHeight() / 2)
        stage.addActor(image1)

        val infiniteLoop1 = RepeatAction()
        infiniteLoop1.count = RepeatAction.FOREVER
        infiniteLoop1.action = overallSequence1
        image1.addAction(infiniteLoop1)

        // Button
        val button1 = TextButton("SPIN!", mySkin, "small")
        button1.setSize(200f, 50f)
        button1.setPosition(width/2f, height/2f, Align.center)
        button1.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                val infiniteLoop1 = RepeatAction()
                infiniteLoop1.count = RepeatAction.FOREVER
                infiniteLoop1.action = overallSequence1
                image1.clearActions()
                image1.addAction(infiniteLoop1)
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val leftBottomTopParallelAction3 = ParallelAction()
                leftBottomTopParallelAction3.addAction(Actions.rotateBy(360f, 1f))
                val infiniteLoop3 = RepeatAction()
                infiniteLoop1.count = RepeatAction.FOREVER
                infiniteLoop1.action = leftBottomTopParallelAction3
                image1.clearActions()
                image1.addAction(infiniteLoop1)
                return true
            }
        })
        stage.addActor(button1)

        fun createSpinner(x: Float, y: Float, rotationAmount: Float) {
            val leftBottomTopParallelAction2 = ParallelAction()
            leftBottomTopParallelAction2.addAction(Actions.rotateBy(rotationAmount, 1f))

            val i = Image(texture)
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

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act()
        stage.draw()
    }
}
