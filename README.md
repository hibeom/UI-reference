# UI-reference

### 1. Canvas 이용한 도넛 차트


![Alt Text](https://media.giphy.com/media/mKC1PBYotnHLt7k5vl/giphy.gif)

#### 구현방법

미션 리스트 갱신 시, DonutView 에 리스트 전달 후 도넛을 그립니다.

1. 미션 리스트 갱신 시, DonutView에 리스트 전달
```kotlin
viewModel.missionList.observe(viewLifecycleOwner, {
    ...
    binding.donutView.missionItems = it
})
```

2. DonutView의 미션 리스트를 set 할 시에, 도넛차트를 그리기 위한 paint 목록 allocation 및 draw 수행
```kotlin
var missionItems = listOf<MissionItem>()
    set(items) {
        field = items
        for (item in items) {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = item.color
            }
            itemPaints.put(item.missionId, paint)
        }
        invalidate()
    }
```
3. DonutView onSizeChanged 에서 도넛 사이즈 설정
```kotlin
override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    radius = (w/2).toFloat() // 기본 도넛 형태를 위한 바깥원 크기
    innerRadius = ((h/4).toFloat()) // 도넛 형태를 위한 내부원 크기

    oval = RectF(0f, 0f, width.toFloat(), height.toFloat()) // drawArc 로 도넛차트를 그리기 위한 원 크기
}
```
4. DonutView onDraw 에서 원 및 호 그리기
```kotlin
override fun onDraw(canvas: Canvas) {
    // 원 중심 coordinate
    val cx = (width/2).toFloat()
    val cy = (height/2).toFloat()

    canvas.run {
        drawCircle(cx, cy, radius, paint) // 기본 바깥 원 그리기

        val totalAmount = getTotalAmount(missionItems) // 비율 계산을 위한 총합 계산
        var lastAngle = 0f // 호를 이어서 그리기 위한 최근 angle 저장
        for (missionItem in missionItems) {
            val ratioAngle = (missionItem.amount.toFloat() / totalAmount)*360f
            val paint = itemPaints.get(missionItem.missionId)
            drawArc(oval, lastAngle, ratioAngle, true, paint!!)
            lastAngle += ratioAngle
        }

        drawCircle(cx, cy, innerRadius, innerPaint)
    }
}
```


### 2. Canvas 이용한 점수 게이지

![Alt Text](https://media.giphy.com/media/ko7eNitZPet7NAF9QT/giphy.gif)

#### 구현방법

점수 게이지를 Canvas 이용해 그린 후, 점수 변경 시 게이지 스틱을 이동시킵니다.

1. onSizeChanged 에서 view 사이즈에 따라 반경과 게이지 gradient 색상을 설정
```kotlin
override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    radius = (w / 2.5f) // 게이지 반경
    smallRadius = (w / 30f) // 게이지 중심의 회색 원 반경

    cx = (w / 2).toFloat() // 게이지 중심 x coordinate
    cy = 0 + radius // 게이지 중심 y coordinate

    oval = RectF(cx - radius, 0f + arcWidth, cx + radius, radius * 2 + arcWidth)

    // 게이지에 gradient 색상 적용
    arcPaint.shader = SweepGradient(cx, cy, gradientColors, gradientPositions).apply {
        val matrix = Matrix().apply {
            setRotate(15f)
        }
        // gradient 색상이 게이지 모양에 맞도록 회전
        setLocalMatrix(matrix)
    }
}
```

2. onDraw 시 게이지 원 draw
```kotlin
override fun onDraw(canvas: Canvas) {
    canvas.run {
        drawArc(oval, 165f, 210f, false, arcPaint) // 게이지 바깥 원
        drawCircle(cx, cy, smallRadius, grayPaint) // 게이지 내부 회색 원
        ...
    }
}
```

3. 점수를 set 하는 경우, 막대를 회전하는 애니메이션 시작
```kotlin
var score = 0
    set(value) {
        field = value
        startAnimation()
    }
```
```kotlin
private fun startAnimation() {
    launch {
        delay(200)
        val animator = ValueAnimator.ofInt(165, (165 + (score/1000f)*210).toInt()).apply {
            duration = 800
            interpolator = LinearInterpolator()
            addUpdateListener {
                stickAngle = it.animatedValue as Int // 애니메이션이 진행되면서 각도 변경 후 draw 명령
                invalidate()
            }
        }
        animator.start()
    }
}
```

4. onDraw 에서 막대의 각도에 따라 막대색상과 시작점 좌표를 계산하여 막대 draw
```kotlin
override fun onDraw(canvas: Canvas) {
    canvas.run {
        ...

        val sx = cx + radius * cos(stickAngle.toFloat() * Math.PI.toFloat() / 180f) * 0.7f // 막대 시작점 x coordinate
        val sy = cy + radius * sin(stickAngle.toFloat() * Math.PI.toFloat() / 180f) * 0.7f // 막대 시작점 y coordinate
        stickPaint.color = getColorFromGradient((stickAngle - 165)/210f)  // 각도에 따라 막대 gradient 색상 결정
        drawLine(sx, sy, cx, cy, stickPaint) // 막대 끝점은 게이지의 중심

        drawText("$score", cx-10f, cy + radius*2/3, textPaint)
        drawText("점", cx+25f*(log10(score.toFloat() + 1).toInt() + 2), cy + radius*2/3, smallTextPaint)
    }
}

```
