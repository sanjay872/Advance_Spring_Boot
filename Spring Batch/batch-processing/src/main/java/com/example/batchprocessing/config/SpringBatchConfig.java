package com.example.batchprocessing.config;

import com.example.batchprocessing.entity.Customer;
import com.example.batchprocessing.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    @Autowired
    private CustomerRepository customerRepository; // to store the data

    @Bean
    public FlatFileItemReader<Customer> reader(){ // read information from the source, i.e. CSV file
        FlatFileItemReader<Customer> itemReader=new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/data/customers.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1); // to skip the first row, because it has the columns title
        itemReader.setLineMapper(lineMapper()); // lineMapper is the function that will hold the details of what column need to be mapped to which entity variable
        return itemReader;
    }

    private LineMapper<Customer> lineMapper(){
        DefaultLineMapper<Customer> lineMapper=new DefaultLineMapper<>();

        //line tokenizer - to get the data from csv file
        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
        lineTokenizer.setNames("id","firstName","lastName","email","gender","contact","country","dob");
        lineTokenizer.setDelimiter(","); // delimiter to separate the value
        lineTokenizer.setStrict(false);

        //map data we got from tokenizer into customer entity
        BeanWrapperFieldSetMapper<Customer> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        //now set these components in the line mapper
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return  lineMapper;
    }

    @Bean
    public CustomerProcessor processor(){ // to process data, if required
        return new CustomerProcessor();
    }

    @Bean
    public RepositoryItemWriter<Customer> writer(){ // to write data into DB
        RepositoryItemWriter<Customer> writer=new RepositoryItemWriter<>();
        writer.setRepository(customerRepository); // repository in which data need to be saved
        writer.setMethodName("save"); // method name
        return writer;
    }

    @Bean
    public Step csvStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("myStep",jobRepository)
                .<Customer,Customer>chunk(10,transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob(JobRepository jobRepository, Step step){
        return new JobBuilder("myJob",jobRepository).start(step).build();
    }

}
