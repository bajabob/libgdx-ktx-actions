package com.central.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.central.Application
import ktx.app.KtxScreen
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.ScreenViewport

class Game(val application: Application) : KtxScreen {

    private lateinit var stage: Stage
    private val width = Gdx.graphics.width.toFloat()
    private val height = Gdx.graphics.height.toFloat()

    override fun show() {
        super.show()

        stage = Stage(ScreenViewport())
        val texture = Texture(Gdx.files.internal("image.jpg"))

        val X_left = Gdx.graphics.width / 3 - texture.width / 2f
        val X_right = Gdx.graphics.width * 2 / 3 - texture.width / 2f
        val Y_top = Gdx.graphics.height * 2 / 3 - texture.height / 2f
        val Y_bottom = Gdx.graphics.height / 3 - texture.height / 2f

        val image1 = Image(texture)
        image1.setPosition(X_left, Y_top)
        image1.setOrigin(image1.getWidth() / 2, image1.getHeight() / 2)
        stage.addActor(image1)

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

        val overallSequence = SequenceAction()
        overallSequence.addAction(topLeftRightParallelAction)
        overallSequence.addAction(moveBottomRightAction)
        overallSequence.addAction(bottomLeftRightParallelAction)
        overallSequence.addAction(leftBottomTopParallelAction)

        val infiniteLoop = RepeatAction()
        infiniteLoop.count = RepeatAction.FOREVER
        infiniteLoop.action = overallSequence
        image1.addAction(infiniteLoop)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act()
        stage.draw()
    }
}
