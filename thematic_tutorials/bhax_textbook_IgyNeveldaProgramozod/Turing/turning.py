class TurnIterator:
    def __init__(self):
      self.steps = 0
      self.steplimit = 1
    def next(self):
      self.steps+=1
      if self.steps > math.floor(self.steplimit):
        self.steps = 0
        self.steplimit += 0.5
        return True
      else:
        return False