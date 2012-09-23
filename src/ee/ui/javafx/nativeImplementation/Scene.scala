package ee.ui.javafx.nativeImplementation

import ee.ui.nativeImplementation.NativeImplementation
import com.sun.javafx.tk.TKScene
import com.sun.javafx.tk.TKSceneListener
import com.sun.javafx.tk.TKScenePaintListener
import ee.ui.javafx.application.Toolkit
import com.sun.javafx.tk.TKPulseListener
import com.sun.javafx.tk.TKDropTargetListener
import javafx.scene.input.InputMethodRequests
import ee.ui.traits.Position
import ee.ui.traits.Size
import javafx.scene.input.MouseEvent
import javafx.event.EventType
import javafx.scene.input.ScrollEvent
import javafx.scene.input.ZoomEvent
import javafx.scene.input.RotateEvent
import javafx.scene.input.SwipeEvent
import javafx.scene.input.TouchPoint

class Scene(val implemented:ee.ui.nativeElements.Scene) extends NativeImplementation with NativeManagerDependencies with Toolkit {
	def init = {}
	
	private var internalScene:Option[TKScene] = None
	
	def initInternalScene(internalScene:TKScene) = {
	  internalScene setTKSceneListener internalSceneListener
	  internalScene setTKScenePaintListener internalScenePaintListener
	  internalScene setScene this
	  
	  internalScene setRoot implemented.root.map(_.nativeImplementation.internalNode).orNull
	  
	  val javaFxColor = Converters convertColor implemented.fill
	  val toolkitPaint = toolkit getPaint javaFxColor
	  
	  internalScene setFillPaint toolkitPaint
	  
	  internalScene setCamera implemented.camera.map(Converters.convertCamera).orNull
	  
      toolkit addSceneTkPulseListener internalScenePulseListener
       
      toolkit enableDrop(internalScene, internalDropTargetListener)
      toolkit installInputMethodRequests(internalScene, internalInputMethodRequests)
	  
	  this.internalScene = Some(internalScene)
	}
	
	def disposeInternalScene = {
        internalScene foreach { scene =>
	        toolkit removeSceneTkPulseListener internalScenePulseListener
	        scene setScene null
	        internalScene = None
        }
	}
	
	/*
	 * This object exists to that we will not recursively
     * update the internalStage
   	 */
	lazy val scene = new Object with Position with Size
  
	def internalSceneListener = new TKSceneListener {
	  
	    def changedLocation(x:Float, y:Float) = {
            scene.x = x
            scene.y = y
        }

        def changedSize(width:Float, height:Float) = {
          scene.width = width
          scene.height = height
        }

        def mouseEvent(event:AnyRef) = {
          
	    	val javaFxEvent = toolkit convertMouseEventToFX event
	    	val convertedEvent = Converters convertMouseEvent javaFxEvent 
	    	
	    	javaFxEvent.getEventType match {
	    	  case MouseEvent.MOUSE_CLICKED => implemented.onMouseClicked notify convertedEvent
	    	}
        }

        def keyEvent(event:Object) = {
            //Scene.this.impl_processKeyEvent(Toolkit.getToolkit().convertKeyEventToFX(event));
        }

        def inputMethodEvent(event:Object) = {
            //Scene.this.processInputMethodEvent(Toolkit.getToolkit().convertInputMethodEventToFX(event));
        }
/*
        def menuEvent(double x, double y, double xAbs, double yAbs,
                boolean isKeyboardTrigger) {
            Scene.this.processMenuEvent(x, y, xAbs,yAbs, isKeyboardTrigger);
        }
*/
       def scrollEvent(
                eventType:EventType[ScrollEvent],
                scrollX:Double, scrollY:Double,
                totalScrollX:Double, totalScrollY:Double,
                xMultiplier:Double, yMultiplier:Double,
                touchCount:Double,
                scrollTextX:Double, scrollTextY:Double,
                defaultTextX:Double, defaultTextY:Double,
                x:Double, y:Double, screenX:Double, screenY:Double,
                _shiftDown:Boolean, _controlDown:Boolean, 
                _altDown:Boolean, _metaDown:Boolean,
                _direct:Boolean, _inertia:Boolean) = {
/*
            ScrollEvent.HorizontalTextScrollUnits xUnits = scrollTextX > 0 ?
                    ScrollEvent.HorizontalTextScrollUnits.CHARACTERS :
                    ScrollEvent.HorizontalTextScrollUnits.NONE;
            
            double xText = scrollTextX < 0 ? 0 : scrollTextX * scrollX;

            ScrollEvent.VerticalTextScrollUnits yUnits = scrollTextY > 0 ?
                    ScrollEvent.VerticalTextScrollUnits.LINES :
                    (scrollTextY < 0 ? 
                        ScrollEvent.VerticalTextScrollUnits.PAGES :
                        ScrollEvent.VerticalTextScrollUnits.NONE);
                    
            double yText = scrollTextY < 0 ? scrollY : scrollTextY * scrollY;

            xMultiplier = defaultTextX > 0 && scrollTextX >= 0
                    ? Math.round(xMultiplier * scrollTextX / defaultTextX)
                    : xMultiplier;

            yMultiplier = defaultTextY > 0 && scrollTextY >= 0
                    ? Math.round(yMultiplier * scrollTextY / defaultTextY)
                    : yMultiplier;

            if (eventType == ScrollEvent.SCROLL_FINISHED) {
                x = scrollGesture.sceneCoords.getX();
                y = scrollGesture.sceneCoords.getY();
                screenX = scrollGesture.screenCoords.getX();
                screenY = scrollGesture.screenCoords.getY();
            } else if (Double.isNaN(x) || Double.isNaN(y) ||
                    Double.isNaN(screenX) || Double.isNaN(screenY)) {
                if (cursorScenePos == null || cursorScreenPos == null) {
                    return;
                }
                x = cursorScenePos.getX();
                y = cursorScenePos.getY();
                screenX = cursorScreenPos.getX();
                screenY = cursorScreenPos.getY();
            } 

            Scene.this.processGestureEvent(ScrollEvent.impl_scrollEvent(
                    eventType,
                    scrollX * xMultiplier, scrollY * yMultiplier,
                    totalScrollX * xMultiplier, totalScrollY * yMultiplier,
                    xUnits, xText, yUnits, yText,
                    touchCount,
                    x, y, screenX, screenY, 
                    _shiftDown, _controlDown, _altDown, _metaDown, 
                    _direct, _inertia),
                    scrollGesture);
                    */
        }

        def zoomEvent(
                eventType:EventType[ZoomEvent],
                zoomFactor:Double, totalZoomFactor:Double,
                x:Double, y:Double, screenX:Double, screenY:Double,
                _shiftDown:Boolean, _controlDown:Boolean,
                _altDown:Boolean, _metaDown:Boolean,
                _direct:Boolean, _inertia:Boolean) = {
/*
            if (eventType == ZoomEvent.ZOOM_FINISHED) {
                x = zoomGesture.sceneCoords.getX();
                y = zoomGesture.sceneCoords.getY();
                screenX = zoomGesture.screenCoords.getX();
                screenY = zoomGesture.screenCoords.getY();
            } else if (Double.isNaN(x) || Double.isNaN(y) ||
                    Double.isNaN(screenX) || Double.isNaN(screenY)) {
                if (cursorScenePos == null || cursorScreenPos == null) {
                    return;
                }
                x = cursorScenePos.getX();
                y = cursorScenePos.getY();
                screenX = cursorScreenPos.getX();
                screenY = cursorScreenPos.getY();
            }

            Scene.this.processGestureEvent(ZoomEvent.impl_zoomEvent(
                    eventType, zoomFactor, totalZoomFactor,
                    x, y, screenX, screenY,
                    _shiftDown, _controlDown, _altDown, _metaDown, 
                    _direct, _inertia),
                    zoomGesture);
                    */
        }

        def rotateEvent(
                eventType:EventType[RotateEvent], 
                angle:Double, totalAngle:Double,
                x:Double, y:Double, screenX:Double, screenY:Double,
                _shiftDown:Boolean, _controlDown:Boolean,
                _altDown:Boolean, _metaDown:Boolean,
                _direct:Boolean, _inertia:Boolean) = {
/*
            if (eventType == RotateEvent.ROTATION_FINISHED) {
                x = rotateGesture.sceneCoords.getX();
                y = rotateGesture.sceneCoords.getY();
                screenX = rotateGesture.screenCoords.getX();
                screenY = rotateGesture.screenCoords.getY();
            } else if (Double.isNaN(x) || Double.isNaN(y) ||
                    Double.isNaN(screenX) || Double.isNaN(screenY)) {
                if (cursorScenePos == null || cursorScreenPos == null) {
                    return;
                }
                x = cursorScenePos.getX();
                y = cursorScenePos.getY();
                screenX = cursorScreenPos.getX();
                screenY = cursorScreenPos.getY();
            }

            Scene.this.processGestureEvent(RotateEvent.impl_rotateEvent(
                    eventType, angle, totalAngle, x, y, screenX, screenY,
                    _shiftDown, _controlDown, _altDown, _metaDown, 
                    _direct, _inertia),
                    rotateGesture);
*/
        }

       def swipeEvent(
                eventType:EventType[SwipeEvent], 
                touchCount:Int,
                x:Double, y:Double, screenX:Double, screenY:Double,
                _shiftDown:Boolean, _controlDown:Boolean,
                _altDown:Boolean, _metaDown:Boolean, _direct:Boolean) = {
/*
            if (Double.isNaN(x) || Double.isNaN(y) ||
                    Double.isNaN(screenX) || Double.isNaN(screenY)) {
                if (cursorScenePos == null || cursorScreenPos == null) {
                    return;
                }
                x = cursorScenePos.getX();
                y = cursorScenePos.getY();
                screenX = cursorScreenPos.getX();
                screenY = cursorScreenPos.getY();
            }

            Scene.this.processGestureEvent(SwipeEvent.impl_swipeEvent(
                    eventType, touchCount, x, y, screenX, screenY,
                    _shiftDown, _controlDown, _altDown, _metaDown, _direct), 
                    swipeGesture);
                    */
        }

        def touchEventBegin(
                time:Long, touchCount:Int, isDirect:Boolean,
                _shiftDown:Boolean, _controlDown:Boolean,
                _altDown:Boolean, _metaDown:Boolean) = {
/*
            nextTouchEvent = TouchEvent.impl_touchEvent(
                    TouchEvent.ANY, null, null, 0,
                    _shiftDown, _controlDown, _altDown, _metaDown);
            nextTouchEvent.impl_setDirect(isDirect);
            if (touchPoints == null || touchPoints.length != touchCount) {
                touchPoints = new TouchPoint[touchCount];
            }
            touchPointIndex = 0;
            */
        }

        def touchEventNext(
                state:TouchPoint.State, touchId:Long,
                x:Int, y:Int, xAbs:Int, yAbs:Int) = {
/*
            touchPointIndex++;
            int id = (state == TouchPoint.State.PRESSED
                    ? touchMap.add(touchId) :  touchMap.get(touchId));
            if (state == TouchPoint.State.RELEASED) {
                touchMap.remove(touchId);
            }
            int order = touchMap.getOrder(id);

            if (!nextTouchEvent.impl_isDirect()) {
                order = touchPointIndex - 1;
            }

            if (order >= touchPoints.length) {
                throw new RuntimeException("Too many touch points reported");
            }

            touchPoints[order] = TouchPoint.impl_touchPoint(id, state,
                    x, y, xAbs, yAbs);
                    */
        }

       def touchEventEnd() = {
         /*
            if (touchPointIndex != touchPoints.length) {
                throw new RuntimeException("Wrong number of touch points reported");
            }

            // for now we don't want to process indirect touch events
            if (nextTouchEvent.impl_isDirect()) {
                Scene.this.processTouchEvent(nextTouchEvent, touchPoints);
            }

            if (touchMap.cleanup()) {
                // gesture finished
                touchEventSetId = 0;
            }
            */
        }
	}
	
	def internalScenePaintListener = new TKScenePaintListener {
	  
	}
	
	lazy val internalScenePulseListener = new TKPulseListener {
	  
	}
	
	def internalDropTargetListener = new TKDropTargetListener {
	  
	}
	
	lazy val internalInputMethodRequests = new InputMethodRequests {
	  
	}
}