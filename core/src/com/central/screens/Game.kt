package com.central.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.central.Application
import ktx.app.KtxScreen


class Game(val application: Application) : KtxScreen {

    private lateinit var stage: Stage
    private val width = Gdx.graphics.width.toFloat()
    private val height = Gdx.graphics.height.toFloat()
    private var Help_Guides = 12
    private var rowHeight = Gdx.graphics.width / 12f
    private var colWidth = Gdx.graphics.width / 12f
    private lateinit var outputLabel: Label

    override fun show() {
        super.show()

        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage

        val mySkin = Skin(Gdx.files.internal("skin/glassy-ui.json"))

        val title = Label("Buttons with Skins", mySkin, "big-black")
        title.setSize(width, rowHeight * 2)
        title.setPosition(0f, height - rowHeight * 2)
        title.setAlignment(Align.center)
        stage.addActor(title)

        // Button
        val button1 = Button(mySkin, "small")
        button1.setSize(colWidth * 4, rowHeight)
        button1.setPosition(colWidth, height - rowHeight * 3)
        button1.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                outputLabel.setText("Press a Button")
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                outputLabel.setText("Pressed Button")
                return true
            }
        })
        stage.addActor(button1)

        // Text Button
        val button2 = TextButton("Text Button", mySkin, "small")
        button2.setSize(colWidth * 4, rowHeight)
        button2.setPosition(colWidth * 7, height - rowHeight * 3)
        button2.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                outputLabel.setText("Press a Button")
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                outputLabel.setText("Pressed Text Button")
                return true
            }
        })
        stage.addActor(button2)

        // ImageButton
        val button3 = ImageButton(mySkin)
        button3.setSize(colWidth * 4, (rowHeight * 2) as Float)
        button3.style.imageUp = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("switch_off.png"))))
        button3.style.imageDown = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("switch_on.png"))))
        button3.setPosition(colWidth, height - rowHeight * 6)
        button3.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                outputLabel.setText("Press a Button")
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                outputLabel.setText("Pressed Image Button")
                return true
            }
        })
        stage.addActor(button3)

        //ImageTextButton
        val button4 = ImageTextButton("ImageText Btn", mySkin, "small")
        button4.setSize(colWidth * 4, (rowHeight * 2) as Float)
        button4.style.imageUp = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("switch_off.png"))))
        button4.style.imageDown = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("switch_on.png"))))
        button4.setPosition(colWidth * 7, height - rowHeight * 6)
        button4.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                outputLabel.setText("Press a Button")
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                outputLabel.setText("Pressed Image Text Button")
                return true
            }
        })
        stage.addActor(button4)

        outputLabel = Label("Press a Button", mySkin, "black")
        outputLabel.setSize(width, rowHeight)
        outputLabel.setPosition(0f, rowHeight)
        outputLabel.setAlignment(Align.center)
        stage.addActor(outputLabel)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act()
        stage.draw()
    }
}
