const proportion = (outMin, outMax) => (value, inMin, inMax) => {
  const limitedValue = Math.max(Math.min(value, inMax), inMin);
  const inputRatio = (limitedValue - inMin) / (inMax - inMin);
  return outMin + inputRatio * (outMax - outMin);
};

export const relativise = (eventLocation, targetBox, max) => {
  const scale = proportion(-max, max);

  return {
    x: scale(eventLocation.x, targetBox.left, targetBox.right),
    y: scale(eventLocation.y, targetBox.top, targetBox.bottom)
  };
};
