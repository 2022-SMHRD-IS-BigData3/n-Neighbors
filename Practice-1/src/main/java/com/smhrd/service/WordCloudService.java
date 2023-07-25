package com.smhrd.service;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.Files;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smhrd.entity.tb_chat;
import com.smhrd.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.palette.ColorPalette;

//"javax.imageio.IIOException: Invalid argument to native writeImage" indicates that there is an issue with writing the image data using the ImageIO library.
// javax.imageio.IIOException: Invalid argument to native writeImage
@Service
@RestController
@RequiredArgsConstructor
@RequestMapping
public class WordCloudService {
	
	private final ChatMessageRepository chatMessageRepository;
	
	@GetMapping("word")
	public String displayWordCloud(Model model) throws IOException {

		// 1단계: 메시지에서 단어 빈도 계산
		Map<String, Integer> wordFrequencyMap = calculateWordFrequenciesForFWordMessages();
		// 2단계: 워드 주파수 맵을 사용하여 워드 클라우드 이미지 생성
		byte[] wordCloudImage = generateWordCloudImage(wordFrequencyMap);

		System.out.println("wordCloudImage => " + wordCloudImage);
		// 3단계: 바이트 배열을 base64 인코딩 문자열로 변환
		String base64Image = Base64.getEncoder().encodeToString(wordCloudImage);

		System.out.println("base64Image =>" + base64Image);
		// 4단계: Thymeleaf 템플릿에 표시할 Base64 인코딩 이미지를 모델에 추가합니다
		model.addAttribute("wordCloudImage", base64Image);
		// 5단계: 단어를 표시할 Thymeleaf 템플릿의 이름을 반환합니다 구름

		return "dashBoard";
		
	}
	
	
	private Map<String, Integer> calculateWordFrequenciesForFWordMessages() {
		// 단어 빈도를 저장할 지도 만들기
		Map<String, Integer> wordFrequencyMap = new HashMap<>();
		// 단어 빈도를 저장할 지도 만들기
		List<tb_chat> fwordMessages = chatMessageRepository.findByType(tb_chat.MessageType.FWORD);
		// 메시지를 반복합니다

		for (tb_chat message : fwordMessages) {
			String textData = message.getMessage();
			System.out.println("textData =>" + textData);
			// 텍스트 데이터에서 알파벳이 아닌 문자 및 공백 제거
			textData = textData.replaceAll("[^a-zA-Z가-힣 ]", "");
			// 텍스트를 개별 단어로 분할
			String[] words = textData.split("\\s+");
			System.out.println("words =>" + words);
			// 각 단어의 발생 횟수 카운트 및 빈도 맵 업데이트
			for (String word : words) {
				wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
				System.out.println("반복문 =>" + wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1));
			}
		}
		return wordFrequencyMap;
	}

	

	private byte[] generateWordCloudImage(Map<String, Integer> wordFrequencyMap) throws IOException {
		// 단어 클라우드 이미지의 차원 설정(x축, y축)
		Dimension dimension = new Dimension(30, 20);
		// 지정된 차원 및 충돌 모드를 사용하여 WordCloud 인스턴스 생성
		WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);// 'CollisionMode.PIXEL_PERFECT'는 
																					//이미지에서 단어가 겹치는 것을 방지하는 데 도움이 되는 
																					//정확한 픽셀 수준 충돌로 단어 구름이 생성되도록 지정

		System.out.println("dimension =>" + dimension);
		System.out.println("wordCloud =>" + wordCloud);
		
		// 단어 클라우드의 속성 설정
		wordCloud.setPadding(2); // 단어사이의 간격 설정
		wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW));
		// 단어 빈도수에 따라 글꼴 크기 설정. => 빈도수가 작으면 작게 크면 크게
		wordCloud.setFontScalar(new LinearFontScalar(10, 20));
		wordCloud.setKumoFont(new KumoFont("NanumGothic", FontWeight.PLAIN));//FontWeight.PLAIN'은 글꼴이 일반(보통) 두께로 사용되도록 지정

		// WordFrequency 맵에서 WordFrequency 개체 목록 만들기
		List<WordFrequency> wordFrequencies = createWordFrequencies(wordFrequencyMap);
		// WordFrequency 개체 목록을 사용하여 Word 클라우드 구축
		wordCloud.build(wordFrequencies); 
		// WordFrequency 목록과 함께 build() 메서드 사용 물건들
		System.out.println("wordFrequencies =>" + wordFrequencies);
		// 클라우드라는 단어를 바이트 배열 출력 스트림에 PNG 이미지로 씁니다
		ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
		wordCloud.writeToStreamAsPNG(imageOutputStream);
		// 바이트 배열 출력 스트림을 바이트 배열로 변환하고 반환
		return imageOutputStream.toByteArray();
	}

	
	private List<WordFrequency> createWordFrequencies(Map<String, Integer> wordFrequencyMap) {
		// WordFrequency 개체를 저장할 목록 만들기
		List<WordFrequency> wordFrequencies = new ArrayList<>();
		// 단어 빈도 맵을 반복하고 각 항목을 WordFrequency 개체로 변환합니다
		for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
			String word = entry.getKey();
			System.out.println("word+entry=>" + word);
			int frequency = entry.getValue();
			System.out.println("frequency=>" + frequency);
			wordFrequencies.add(new WordFrequency(word, frequency));
		}
		// WordFrequency 개체 목록 반환
		return wordFrequencies;
	}
	
}

// // 1단계: 메시지에서 단어 빈도 계산
//		Map<String, Integer> wordFrequencyMap = calculateWordFrequenciesForFWordMessages();
//		// 2단계: 워드 주파수 맵을 사용하여 워드 클라우드 이미지 생성
//		byte[] wordCloudImage = generateWordCloudImage(wordFrequencyMap);
//
//		System.out.println("wordCloudImage => " + wordCloudImage);
//		// 3단계: 바이트 배열을 base64 인코딩 문자열로 변환
//		String base64Image = Base64.getEncoder().encodeToString(wordCloudImage);
//
//		System.out.println("base64Image =>" + base64Image);
//		// 4단계: Thymeleaf 템플릿에 표시할 Base64 인코딩 이미지를 모델에 추가합니다
//		model.addAttribute("wordCloudImage", base64Image);
//		// 5단계: 단어를 표시할 Thymeleaf 템플릿의 이름을 반환합니다 구름
//
//		return "wordcloud";
//	}
//	
//	
//	private Map<String, Integer> calculateWordFrequenciesForFWordMessages() {
//		// 단어 빈도를 저장할 지도 만들기
//		Map<String, Integer> wordFrequencyMap = new HashMap<>();
//		// 단어 빈도를 저장할 지도 만들기
//		List<tb_chat> fwordMessages = chatMessageRepository.findByType(tb_chat.MessageType.FWORD);
//		// 메시지를 반복합니다
//
//		for (tb_chat message : fwordMessages) {
//			String textData = message.getMessage();
//			System.out.println("textData =>" + textData);
//			// 텍스트 데이터에서 알파벳이 아닌 문자 및 공백 제거
//			textData = textData.replaceAll("[^a-zA-Z가-힣 ]", "");
//			// 텍스트를 개별 단어로 분할
//			String[] words = textData.split("\\s+");
//			System.out.println("words =>" + words);
//			// 각 단어의 발생 횟수 카운트 및 빈도 맵 업데이트
//			for (String word : words) {
//				wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
//				System.out.println("반복문 =>" + wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1));
//			}
//		}
//		return wordFrequencyMap;
//	}
//
//	
//
//	private byte[] generateWordCloudImage(Map<String, Integer> wordFrequencyMap) throws IOException {
//		// 단어 클라우드 이미지의 차원 설정
//		Dimension dimension = new Dimension(6, 4);
//		// 지정된 차원 및 충돌 모드를 사용하여 WordCloud 인스턴스 생성
//		WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
//
//		System.out.println("dimension =>" + dimension);
//		System.out.println("wordCloud =>" + wordCloud);
//		
//		// 단어 클라우드의 속성 설정
//		wordCloud.setPadding(2);
//		wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW));
//		wordCloud.setFontScalar(new LinearFontScalar(10, 40));
//		wordCloud.setKumoFont(new KumoFont("Arial", FontWeight.PLAIN));
//
//		// WordFrequency 맵에서 WordFrequency 개체 목록 만들기
//		List<WordFrequency> wordFrequencies = createWordFrequencies(wordFrequencyMap);
//		// WordFrequency 개체 목록을 사용하여 Word 클라우드 구축
//		wordCloud.build(wordFrequencies); 
//		// WordFrequency 목록과 함께 build() 메서드 사용 물건들
//		System.out.println("wordFrequencies =>" + wordFrequencies);
//		// 클라우드라는 단어를 바이트 배열 출력 스트림에 PNG 이미지로 씁니다
//		ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
//		wordCloud.writeToStreamAsPNG(imageOutputStream);
//		// 바이트 배열 출력 스트림을 바이트 배열로 변환하고 반환
//		return imageOutputStream.toByteArray();
//	}
//
//	
//	private List<WordFrequency> createWordFrequencies(Map<String, Integer> wordFrequencyMap) {
//		// WordFrequency 개체를 저장할 목록 만들기
//		List<WordFrequency> wordFrequencies = new ArrayList<>();
//		// 단어 빈도 맵을 반복하고 각 항목을 WordFrequency 개체로 변환합니다
//		for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
//			String word = entry.getKey();
//			System.out.println("word+entry=>" + word);
//			int frequency = entry.getValue();
//			System.out.println("frequency=>" + frequency);
//			wordFrequencies.add(new WordFrequency(word, frequency));
//		}
//		// WordFrequency 개체 목록 반환
//		return wordFrequencies;
//	}

//}

//	@GetMapping("/word")
//    public byte[] generateWordCloudImage() throws IOException {
//        List<tb_chat> fwordMessages = chatMessageRepository.findByType(tb_chat.MessageType.FWORD);
//        Map<String, Integer> wordFrequencyMap = new HashMap<>();
//
//        for (tb_chat message : fwordMessages) {
//            String textData = message.getMessage();
//            System.out.println("textData => " + textData);
//         // 텍스트를 개별 단어로 분할
//            String[] words = textData.split("\\s+");
//            System.out.println("words => " + words);
//         // 각 단어의 발생 횟수 카운트 및 빈도 맵 업데이트
//            for (String word : words) {
//                wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
//                System.out.println("wordFre => "+ wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1));
//            }
//        }
//
//        return generateWordCloudImage(wordFrequencyMap);
//    }
//
//    private byte[] generateWordCloudImage(Map<String, Integer> wordFrequencyMap) throws IOException {
//        Dimension dimension = new Dimension(600, 400);
//        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
//        wordCloud.setPadding(2);
//        wordCloud.setBackground(new PixelBoundryBackground("/path/to/your/image/background.png"));
//        wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW));
//        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
//        wordCloud.setKumoFont(new KumoFont("Arial", FontWeight.PLAIN));
//
//     // WordFrequencyMap을 사용하여 WordFrequency 개체 목록 만들기
//        List<WordFrequency> wordFrequencies = new ArrayList<>();
//        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
//            wordFrequencies.add(new WordFrequency(entry.getKey(), entry.getValue()));
//            System.out.println("22wordFre => "+wordFrequencies.add(new WordFrequency(entry.getKey(), entry.getValue())));
//        }
//
//     // WordFrequency 개체 목록을 워드 클라우드에 추가
//        wordCloud.build(wordFrequencies);
//
//        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
//        wordCloud.writeToStreamAsPNG(imageOutputStream);
//        System.out.println("wordCloud");
//        return imageOutputStream.toByteArray();
//    }

//	@GetMapping("/word")
//	public Map<String, Integer> calculateWordFrequenciesForFWordMessages(){
//		Map<String, Integer> wordFrequencyMap = new HashMap<>();
//		List<tb_chat> fwordMessages = chatMessageRepository.findByType(tb_chat.MessageType.FWORD);
//		System.out.println("fwordMessages => "+fwordMessages);
//		
//		for(tb_chat message : fwordMessages) {
//			String textData = message.getMessage();
//			System.out.println("textData => "+textData);
//			// text 구두점을 제거
//			
//			
//			textData = textData.replaceAll("[^a-zA-Z ]", "");
//			System.out.println("textData22 => "+textData);
//			// 텍스트를 개별 단어로 분할
//			String[] words = textData.split("\\s+");
//			System.out.println("words => "+ words);
//			// 각 단어의 발생 횟수를 세고 빈도 지도를 업데이트합니다
//			for(String word: words) {
//				wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0)+1);
//				System.out.println(wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0)+1));
//			}
//		}
//		
//		return wordFrequencyMap;
//
//	}

//public Map<String, Integer> calculateWordFrequenciesForFWordMessages(){
//Map<String, Integer> wordFrequencyMap = new HashMap<>();
//List<tb_chat> fwordMessages = chatMessageRepository.findByType(tb_chat.MessageType.FWORD);
//System.out.println("fwordMessages => "+fwordMessages);
//
//for(tb_chat message : fwordMessages) {
//	String textData = message.getMessage();
//	System.out.println("textData => "+textData);
//	// text 구두점을 제거
//	
//	
//	textData = textData.replaceAll("[^a-zA-Z ]", "");
//	System.out.println("textData22 => "+textData);
//	// 텍스트를 개별 단어로 분할
//	String[] words = textData.split("\\s+");
//	System.out.println("words => "+ words);
//	// 각 단어의 발생 횟수를 세고 빈도 지도를 업데이트합니다
//	for(String word: words) {
//		wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0)+1);
//		System.out.println(wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0)+1));
//	}
//}
//
//return wordFrequencyMap;
//
//}