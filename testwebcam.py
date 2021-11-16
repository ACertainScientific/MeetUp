import torch
# import cv2
import pydirectinput
import pyautogui

model = torch.hub.load('ultralytics/yolov5', 'yolov5n6')  # or yolov5m, yolov5l, yolov5x, custom
# cap = cv.VideoCapture(0)
# cap.set(cv.CAP_PROP_FRAME_WIDTH, 1920)
# cap.set(cv.CAP_PROP_FRAME_HEIGHT, 1080)
model.conf=0.3



import cv2, queue, threading, time

# bufferless VideoCapture
class VideoCapture:

  def __init__(self, name):
    self.cap = cv2.VideoCapture(name)
    self.cap.set(cv2.CAP_PROP_FRAME_WIDTH, 1920)
    self.cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 1080)
    self.q = queue.Queue()
    t = threading.Thread(target=self._reader)
    t.daemon = True
    t.start()

  # read frames as soon as they are available, keeping only most recent one
  def _reader(self):
    while True:
      ret, frame = self.cap.read()
      if not ret:
        break
      if not self.q.empty():
        try:
          self.q.get_nowait()   # discard previous (unprocessed) frame
        except queue.Empty:
          pass
      self.q.put(frame)

  def read(self):
    return self.q.get()

# cap = VideoCapture(0)
# while True:
#   time.sleep(.5)   # simulate time between events
#   frame = cap.read()
#   cv2.imshow("frame", frame)
#   if chr(cv2.waitKey(1)&255) == 'q':
#     break



def camera(i):
    # cap = cv2.VideoCapture(i)
    # cap.set(cv2.CAP_PROP_FRAME_WIDTH, 1920)
    # cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 1080)
    # cap.set(cv2.CAP_PROP_BUFFERSIZE, 0)
    cap = VideoCapture(0)
    # if not cap.isOpened():
    #     print("Cannot open camera")
    #     exit()
    while True:
        # Capture frame-by-frame
        # ret, frame = cap.read()
        frame = cap.read()
        # if frame is read correctly ret is True
        # if not ret:
        #     print("Can't receive frame (stream end?). Exiting ...")
        #     break
        # cv.imshow('frame',frame)
        # if cv.waitKey(1) == ord('q'):
        #     break
        results = model(frame)
        # results.print()
        respanda = results.pandas().xyxy[0].to_dict("index")
        # print(respanda.get("name").loc[0])
        # print(respanda)
        xmin=0
        xmax=0
        ymin=0
        ymax=0
        flag = False
        for i in respanda.keys():
            if(respanda.get(i).get("name") == "person"):
                # print(i)
                # print(respanda.get(i).get("name"))
                flag=True
                xmin = respanda.get(i).get("xmin")
                ymin = respanda.get(i).get("ymin")
                xmax = respanda.get(i).get("xmax")
                ymax = respanda.get(i).get("ymax")
                print(respanda.get(i).get("confidence"))
                
        if flag==True:
            
            # x_move = int(0+((xmin+xmax)/2))-960
            # y_move = int(0+((ymin + ymax)/2))-540
            # base_Step = 10
            # if(x_move>0 and y_move > 0):
            #     pydirectinput.move(min(base_Step,x_move),min(base_Step,y_move),relative=True)
            # if(x_move>0 and y_move < 0):
            #     pydirectinput.move(min(base_Step,x_move),max(-base_Step,y_move),relative=True)
            # if(x_move<0 and y_move > 0):
            #     pydirectinput.move(max(-base_Step,x_move),min(base_Step,y_move),relative=True)
            # if(x_move<0 and y_move < 0):
            #     pydirectinput.move(max(-base_Step,x_move),max(-base_Step,y_move),relative=True)


            pydirectinput.move(int(0+((xmin+xmax)/2))-960,int(0+((ymin + ymax)/2))-540,relative=True)


            # pyautogui.moveTo(int(0+((xmin+xmax)/2)), int(0+((ymin + ymax)/2)))
            print((int(0+((xmin+xmax)/2)),int(0+((ymin + ymax)/2))))
            # pydirectinput.moveRel(int(641+((xmin+xmax)/2))-960,int(360+((ymin + ymax)/2))-540,relative=True)
            # pygame.mouse.set_pos(480+((xmin+xmax)/2),270+((ymin + ymax)/2))
            # move_to(handle,int(480+((xmin+xmax)/2)),int(270+((ymin + ymax)/2)))
            # pyautogui.moveTo(0,0)
            # pyautogui.click()
            # m.move(480+((xmin+xmax)/2),270+((ymin + ymax)/2))
            #ctypes.windll.user32.SetCursorPos(int(480+((xmin+xmax)/2)),int(270+((ymin + ymax)/2)))
            # os.system(f"adb shell input tap {int(480+((xmin+xmax)/2))} {int(270+((ymin + ymax)/2))}")
            # x,y = pyautogui.position()
            # print(x,y)
    # When everything done, release the capture 
    cap.release()
    cv.destroyAllWindows()

camera(0)